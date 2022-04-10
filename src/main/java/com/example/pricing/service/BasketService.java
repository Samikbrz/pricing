package com.example.pricing.service;

import com.example.pricing.dto.BasketDto;
import com.example.pricing.dto.ProductDto;
import com.example.pricing.model.Basket;
import com.example.pricing.model.enums.DiscountType;
import com.example.pricing.repository.BasketRepository;
import com.example.pricing.utils.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class BasketService {

    private final BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Transactional
    public Basket addProductToBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    @Transactional
    public BasketDto getBasketItem() {
        List<ProductDto> result = basketRepository.getProductsWithCount();
        if (result.isEmpty()) {
            return new BasketDto();
        }
        return convertProductDtoToBasketDto(result);
    }

    public BasketDto convertProductDtoToBasketDto(List<ProductDto> result) {
        BasketDto basketDto = new BasketDto();
        basketDto.setProducts(result);
        float totalPrice = (float) result.stream().mapToDouble(ProductDto::getTotalPrice).sum();
        basketDto.setTotalPrice(totalPrice);
        findDiscountMethodAndSetReducedPrice(basketDto);
        return basketDto;
    }

    public void findDiscountMethodAndSetReducedPrice(BasketDto basketDto) {
        List<Float> reducedPrices = new ArrayList<>();

        if (basketDto.getProducts().size() > 0) {
            basketDto.setReducedPrice(DiscountUtil.calculateDiscountForSameProducts(basketDto));
            reducedPrices.add(basketDto.getReducedPrice());
        }

        if (basketDto.getTotalPrice() >= 1000) {
            basketDto.setReducedPrice(DiscountUtil.calculateDiscountFor1000AndAbove(basketDto));
            reducedPrices.add(basketDto.getReducedPrice());
        }

        if (basketDto.getProducts().stream().anyMatch(p -> p.getDiscountType().equals(DiscountType.BOGO.toString()))) {
            basketDto.setReducedPrice(DiscountUtil.calculateDiscountForPOGOProducts(basketDto));
            reducedPrices.add(basketDto.getReducedPrice());
        }

        if (reducedPrices.size() > 1) {
            float value = reducedPrices.stream().min(Comparator.naturalOrder()).get();
            basketDto.setReducedPrice(value);
        }
    }

}
