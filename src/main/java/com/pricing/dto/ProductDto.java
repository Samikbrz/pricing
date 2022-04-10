package com.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

   private String productName;

    private Float price;

    private int totalCount;

    private Float totalPrice;

    private String discountType;
}
