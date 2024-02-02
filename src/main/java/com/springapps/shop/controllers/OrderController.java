package com.springapps.shop.controllers;

import com.springapps.shop.entities.Order;
import com.springapps.shop.entities.User;
import com.springapps.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     @PostMapping("/add")
     public ResponseEntity<Order> addOrderByUser(){
        return ResponseEntity.ok(orderService.addOrderToUser());
     }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> viewAllOrderByUser(@PathVariable Long userId){
          return ResponseEntity.ok(orderService.viewOrders(userId));
     }



}
