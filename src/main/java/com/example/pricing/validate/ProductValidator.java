package com.example.pricing.validate;

import com.example.pricing.constant.Messages;
import com.example.pricing.exception.ApiRequestException;
import com.example.pricing.exception.NotValidException;
import com.example.pricing.model.Product;
import org.springframework.http.HttpStatus;

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
        if (result.contains(false)) {
            throw new NotValidException(Messages.PRODUCT_NAME_NOT_VALID);
        }
        return null;
    }

    private final Predicate<Product> isProductNameValid = p -> p.getName() != null && !p.getName().equals("");
}
