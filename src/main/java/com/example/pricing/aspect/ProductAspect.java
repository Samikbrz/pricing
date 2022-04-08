package com.example.pricing.aspect;

import com.example.pricing.model.Product;
import com.example.pricing.validate.ProductValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAspect {

    @Before("execution(* com.example.pricing.controller.ProductController.save(..))")
    public void beforeSaveProduct(JoinPoint joinPoint) {
        new ProductValidator((Product) joinPoint.getArgs()[0]).validate();
    }
}
