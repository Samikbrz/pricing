package com.example.pricing.controller;

import com.example.pricing.constant.Messages;
import com.example.pricing.model.Basket;
import com.example.pricing.model.Product;
import com.example.pricing.service.BasketService;
import com.example.pricing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketService basketService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Product product) {
        productService.saveProduct(product);
        return new ResponseEntity<>(Messages.PRODUCT_ADDED, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable long id) {
        product.setId(id);
        productService.updateProduct(product);
        return new ResponseEntity<>(Messages.PRODUCT_UPDATED, HttpStatus.OK);
    }

    @PostMapping("/addToBasket/{id}")
    public ResponseEntity<Object> addToBasket(@PathVariable long id) {
        Basket basket = new Basket();
        basket.setProductId(id);
        basketService.addProductToBasket(basket);
        return new ResponseEntity<>(Messages.PRODUCT_ADDED, HttpStatus.OK);
    }
}
