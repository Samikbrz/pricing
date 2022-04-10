package com.pricing.controller;

import com.pricing.constant.Messages;
import com.pricing.dto.CartDto;
import com.pricing.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItem());
    }

    @DeleteMapping("/deleteProductFromCart/{id}")
    public ResponseEntity deleteProductFromCart(@PathVariable long id) {
        cartService.deleteProductFromCart(id);
        return new ResponseEntity(Messages.PRODUCT_REMOVED, HttpStatus.OK);
    }

    @PostMapping("/addProduct/{id}")
    public ResponseEntity<Object> addToCart(@PathVariable long id) {
        cartService.addProductToCart(id);
        return new ResponseEntity<>(Messages.PRODUCT_ADDED, HttpStatus.OK);
    }
}
