package com.pricing.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private List<ProductDto> products;

    private float totalPrice;

    private float reducedPrice;
}
