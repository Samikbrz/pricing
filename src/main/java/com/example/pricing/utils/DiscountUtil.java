package com.example.pricing.utils;

import com.example.pricing.dto.BasketDto;
import com.example.pricing.dto.ProductDto;
import com.example.pricing.model.Product;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class DiscountUtil {

    public static float calculateDiscountForSameProducts(BasketDto basketDto) {
        int reduceRate = 15;
        if ((basketDto.getProducts().get(0).getTotalCount() / 3) % 2 == 0) {
            reduceRate *= 2;
        }
        return basketDto.getTotalPrice() - ((basketDto.getTotalPrice() * reduceRate) / 100);
    }

    public static float calculateDiscountFor1000AndAbove(BasketDto basketDto) {
        int reduceRate = 20;

        ProductDto product = basketDto.getProducts()
                .stream()
                .min(Comparator.comparing(ProductDto::getPrice))
                .orElseThrow(NoSuchElementException::new);

        basketDto.setReducedPrice(basketDto.getTotalPrice() - ((product.getTotalPrice() * reduceRate) / 100));

        return basketDto.getReducedPrice();
    }
}
