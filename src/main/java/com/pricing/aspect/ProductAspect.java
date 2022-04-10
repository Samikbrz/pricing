package com.pricing.aspect;

import com.pricing.model.Product;
import com.pricing.validate.ProductValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAspect {

    @Before("execution(* com.pricing.controller.ProductController.save(..))")
    public void beforeSaveProduct(JoinPoint joinPoint) {
        new ProductValidator((Product) joinPoint.getArgs()[0]).validate();
    }

    @Before("execution(* com.pricing.controller.ProductController.updateProduct(..))")
    public void beforeUpdateProduct(JoinPoint joinPoint) {
        new ProductValidator((Product) joinPoint.getArgs()[0]).validate();
    }
}
