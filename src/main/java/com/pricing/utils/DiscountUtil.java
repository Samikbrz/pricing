package com.pricing.utils;

import com.pricing.dto.CartDto;
import com.pricing.dto.ProductDto;
import com.pricing.model.enums.DiscountType;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class DiscountUtil {

    public static float calculateDiscountForSameProducts(CartDto cartDto) {
        int reduceRate = 15;
        float result = 0;

        ProductDto product = getProductHasMinPrice(cartDto.getProducts());

        if (product.getTotalCount() > 2) {
            if (product.getTotalCount() != 3 && product.getTotalCount() % 3 == 0) {
                reduceRate += 15;
                result += product.getTotalPrice() - ((product.getTotalPrice() * reduceRate) / 100);
            } else {
                result += product.getTotalPrice() - ((product.getTotalPrice() * reduceRate) / 100);
            }
        }

        return result == 0 ? product.getTotalPrice() : result;
    }

    public static float calculateDiscountFor1000AndAbove(CartDto cartDto) {
        int reduceRate = 20;

        ProductDto product = getProductHasMinPrice(cartDto.getProducts());
        cartDto.setReducedPrice(cartDto.getTotalPrice() - ((product.getTotalPrice() * reduceRate) / 100));

        return cartDto.getReducedPrice();
    }

    public static float calculateDiscountForPOGOProducts(CartDto cartDto) {

        List<ProductDto> products = cartDto.getProducts()
                .stream()
                .filter(p -> p.getDiscountType().equals(DiscountType.BOGO.toString()) && p.getTotalCount() > 1).collect(Collectors.toList());

        ProductDto product = getProductHasMinPrice(products);

        if (product.getTotalCount() % 2 == 0) {
            cartDto.setReducedPrice(cartDto.getTotalPrice() - (product.getTotalPrice() / 2));
        } else {
            int value = product.getTotalCount() / 2;
            float newPrice = product.getPrice() * value;
            cartDto.setReducedPrice(cartDto.getTotalPrice() - newPrice);
        }

        return cartDto.getReducedPrice();
    }

    private static ProductDto getProductHasMinPrice(List<ProductDto> products) {
        ProductDto product = products
                .stream()
                .min(Comparator.comparing(ProductDto::getPrice))
                .orElseThrow(NoSuchElementException::new);

        return product;
    }
}
