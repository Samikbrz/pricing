package com.pricing.controller;

import com.pricing.constant.Messages;
import com.pricing.model.Product;
import com.pricing.service.ProductService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody @ApiParam Product product) {
        productService.saveProduct(product);
        return new ResponseEntity(Messages.PRODUCT_ADDED, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable long id) {
        product.setId(id);
        productService.updateProduct(product);
        return new ResponseEntity(Messages.PRODUCT_UPDATED, HttpStatus.OK);
    }
}
