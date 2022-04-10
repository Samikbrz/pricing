package com.pricing.service;

import com.pricing.constant.Messages;
import com.pricing.dto.CartDto;
import com.pricing.dto.ProductDto;
import com.pricing.exception.NotFoundException;
import com.pricing.model.Cart;
import com.pricing.model.Product;
import com.pricing.model.enums.DiscountType;
import com.pricing.repository.CartRepository;
import com.pricing.repository.ProductRepository;
import com.pricing.utils.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProductToCart(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        Cart result = cartRepository.findByProductId(id);
        if (result == null) {
            Cart cart = new Cart();
            cart.setProductId(id);
            cart.setTotalCount(1);
            cartRepository.save(cart);
        } else {
            int newCount = result.getTotalCount() + 1;
            cartRepository.updateCountByProductId(newCount, id);
        }
    }

    @Transactional
    public CartDto getCartItem() {
        List<ProductDto> result = cartRepository.getProductsWithCount();
        if (result.isEmpty()) {
            return new CartDto();
        }
        return convertProductDtoToCartDto(result);
    }

    @Transactional
    public void deleteProductFromCart(long productId) {
        Cart cart = cartRepository.findByProductId(productId);

        if (cart == null) {
            throw new NotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        if (cart.getTotalCount() == 1) {
            cartRepository.deleteByProductId(cart.getProductId());
        } else {
            int newCount = cart.getTotalCount() - 1;
            cartRepository.updateCountByProductId(newCount, cart.getProductId());
        }
    }

    public CartDto convertProductDtoToCartDto(List<ProductDto> result) {
        CartDto cartDto = new CartDto();
        cartDto.setProducts(result);
        float totalPrice = (float) result.stream().mapToDouble(ProductDto::getTotalPrice).sum();
        cartDto.setTotalPrice(totalPrice);
        findDiscountMethodAndSetReducedPrice(cartDto);
        return cartDto;
    }

    public void findDiscountMethodAndSetReducedPrice(CartDto cartDto) {

        List<Float> reducedPrices = new ArrayList<>();

        if (cartDto.getTotalPrice() > 0) {
            cartDto.setReducedPrice(DiscountUtil.calculateDiscountForSameProducts(cartDto));
            reducedPrices.add(cartDto.getReducedPrice());
        }

        if (cartDto.getTotalPrice() >= 1000) {
            cartDto.setReducedPrice(DiscountUtil.calculateDiscountFor1000AndAbove(cartDto));
            reducedPrices.add(cartDto.getReducedPrice());
        }

        if (cartDto.getProducts().stream().anyMatch(p -> p.getDiscountType().equals(DiscountType.BOGO.toString()))) {
            cartDto.setReducedPrice(DiscountUtil.calculateDiscountForPOGOProducts(cartDto));
            reducedPrices.add(cartDto.getReducedPrice());
        }

        if (reducedPrices.size() > 1) {
            float value = reducedPrices.stream().min(Comparator.naturalOrder()).get();
            cartDto.setReducedPrice(value);
        }
    }

}
