package com.example.pricing.service;

import com.example.pricing.dto.BasketDto;
import com.example.pricing.dto.ProductDto;
import com.example.pricing.model.Basket;
import com.example.pricing.repository.BasketRepository;
import com.example.pricing.utils.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Basket addProductToBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public BasketDto getBasketItem() {
        List<ProductDto> result = basketRepository.getProductsWithCount();
        return castProductDtoToBasketDto(result);
    }

    public BasketDto castProductDtoToBasketDto(List<ProductDto> result) {
        BasketDto basketDto = new BasketDto();
        basketDto.setProducts(result);
        float totalPrice = (float) result.stream().mapToDouble(ProductDto::getTotalPrice).sum();
        basketDto.setTotalPrice(totalPrice);
        findDiscountMethod(basketDto);
        return basketDto;
    }

    public void findDiscountMethod(BasketDto basketDto) {
        List<Float> reducedPrices = new ArrayList<>();
        if (basketDto.getProducts().size() == 1) {
            basketDto.setReducedPrice(DiscountUtil.calculateDiscountForSameProducts(basketDto));
            reducedPrices.add(basketDto.getReducedPrice());
        }

        if (basketDto.getTotalPrice() >= 1000 && basketDto.getProducts().size() > 1) {
            basketDto.setReducedPrice(DiscountUtil.calculateDiscountFor1000AndAbove(basketDto));
            reducedPrices.add(basketDto.getReducedPrice());
        }

        if (reducedPrices.size() > 1) {
            float value = reducedPrices.stream().min(Comparator.naturalOrder()).get();
            basketDto.setReducedPrice(value);
        }
    }

}
