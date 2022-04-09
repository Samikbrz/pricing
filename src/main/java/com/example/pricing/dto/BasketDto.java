package com.example.pricing.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketDto {

    private List<ProductDto> products;

    private float totalPrice;

    private float reducedPrice;
}
