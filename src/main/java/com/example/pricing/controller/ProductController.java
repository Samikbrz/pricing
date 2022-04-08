package com.example.pricing.controller;

import com.example.pricing.ApiExceptionHandler;
import com.example.pricing.constant.Messages;
import com.example.pricing.exception.ApiRequestException;
import com.example.pricing.model.Product;
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
}
