package com.example.pricing.utils;

import com.example.pricing.dto.BasketDto;
import com.example.pricing.dto.ProductDto;
import com.example.pricing.model.Product;
import com.example.pricing.model.enums.DiscountType;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class DiscountUtil {

    public static float calculateDiscountForSameProducts(BasketDto basketDto) {
        int reduceRate = 15;
        float result = 0;
        for (ProductDto product : basketDto.getProducts()) {
            if (product.getTotalCount() > 2) {
                if (product.getTotalCount() != 3 && product.getTotalCount() % 3 == 0) {
                    reduceRate += 15;
                    result += product.getTotalPrice() - ((product.getTotalPrice() * reduceRate) / 100);
                } else {
                    result += product.getTotalPrice() - ((product.getTotalPrice() * reduceRate) / 100);
                }
            }
        }

        return result;
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

    public static float calculateDiscountForPOGOProducts(BasketDto basketDto) {

        List<ProductDto> products = basketDto.getProducts()
                .stream()
                .filter(p -> p.getDiscountType().equals(DiscountType.BOGO.toString()) && p.getTotalCount() > 1).collect(Collectors.toList());

        for (ProductDto product : products) {
            if (product.getTotalCount() % 2 == 0) {
                basketDto.setReducedPrice(basketDto.getTotalPrice() - (product.getTotalPrice()/2));
            } else {
                int value = product.getTotalCount() / 2;
                float newPrice = product.getPrice() * value;
                basketDto.setReducedPrice(basketDto.getTotalPrice() - newPrice);
            }
        }

        return basketDto.getReducedPrice();
    }
}
