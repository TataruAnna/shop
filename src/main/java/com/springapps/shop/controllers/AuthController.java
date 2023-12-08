package com.springapps.shop.controllers;

import com.springapps.shop.dtos.AuthRequestDTO;
import com.springapps.shop.entities.User;
import com.springapps.shop.services.UserDetailsServiceImpl;
import com.springapps.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate (@RequestBody AuthRequestDTO authRequestDTO){
        return ResponseEntity.ok(userService.authenticate(authRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register (@RequestBody AuthRequestDTO authRequestDTO){
        return ResponseEntity.ok(userService.register(authRequestDTO));
    }
}
