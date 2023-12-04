package com.springapps.shop.controllers;

import com.springapps.shop.entities.Order;
import com.springapps.shop.entities.User;
import com.springapps.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")

public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    //Plasam o comanda pentru un utilizator (cu produsele pe care le are in cosul de cumparaturi)
    //
    //Endpoint: /orders/add/{userId}
     @PostMapping("/{userId}")
     public ResponseEntity<User> addOrderByUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.addOrderToUser(userId));

     }


}
