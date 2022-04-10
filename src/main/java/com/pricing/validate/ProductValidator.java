package com.pricing.validate;

import com.pricing.constant.Messages;
import com.pricing.exception.NotValidException;
import com.pricing.model.Product;
import com.pricing.model.enums.DiscountType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProductValidator {

    private Product product;

    public ProductValidator(Product product) {
        this.product = product;
    }

    public ProductValidator validate() {
        List<Boolean> result = new ArrayList<>();
        result.add(isProductNameValid.test(product));
        result.add(isProductDiscountType.test(product));
        if (result.contains(false)) {
            throw new NotValidException(Messages.PRODUCT_FIELDS_NOT_VALID);
        }
        return null;
    }

    private final Predicate<Product> isProductNameValid = p -> p.getName() != null && !p.getName().equals("");

    private final Predicate<Product> isProductDiscountType = p -> p.getDiscountType().equals(DiscountType.DISCOUNTLESS)
            || p.getDiscountType().equals(DiscountType.BOGO);

}
