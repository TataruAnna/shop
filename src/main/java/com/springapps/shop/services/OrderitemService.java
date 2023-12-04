package com.springapps.shop.services;

import com.springapps.shop.entities.Orderitem;
import com.springapps.shop.repositories.OrderitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderitemService {

    private OrderitemRepository orderitemRepository;


    @Autowired
    public OrderitemService(OrderitemRepository orderitemRepository) {
        this.orderitemRepository = orderitemRepository;
    }




}
