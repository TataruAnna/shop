package com.springapps.shop.controllers;

import com.springapps.shop.dtos.CartRequestDTO;
import com.springapps.shop.dtos.CartResponseDTO;
import com.springapps.shop.entities.CartItem;
import com.springapps.shop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody CartRequestDTO cartRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(cartRequestDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> viewCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.viewCart(userId));
    }
}
