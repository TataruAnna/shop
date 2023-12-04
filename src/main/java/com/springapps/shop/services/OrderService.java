package com.springapps.shop.services;

import com.springapps.shop.dtos.CartItemResponseDTO;
import com.springapps.shop.dtos.OrderItemResponseDTO;
import com.springapps.shop.entities.CartItem;
import com.springapps.shop.entities.Order;
import com.springapps.shop.entities.Orderitem;
import com.springapps.shop.entities.User;
import com.springapps.shop.exceptions.ResourceNotFoundException;
import com.springapps.shop.repositories.CartItemRepository;
import com.springapps.shop.repositories.OrderRepository;
import com.springapps.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;

    private CartItemRepository cartItemRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public OrderItemResponseDTO mapFromCartitemDTOToOrderitemDTO(CartItemResponseDTO cartItem) {
        OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
        orderItemDTO.setId(cartItem.getId());
        orderItemDTO.setPrice(cartItem.getPrice());
        orderItemDTO.setQuantity(cartItem.getQuantity());
        orderItemDTO.setProductName(cartItem.getProductName());
        return orderItemDTO;
    }

    public Orderitem mapFromCartitemtoOrderitem(CartItem cartItem) {
        Orderitem orderitem = new Orderitem();
//        orderitem.setUser(cartItem.getUser());
        orderitem.setId(cartItem.getId());
        orderitem.setQuantity(cartItem.getQuantity());
        orderitem.setProduct(cartItem.getProduct());

        return orderitem;

    }

    //Plasam o comanda pentru un utilizator (cu produsele pe care le are in cosul de cumparaturi)
    //
    //Endpoint: /orders/add/{userId}
    @Transactional
    public User addOrderToUser(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Order order = new Order();

        List<Orderitem> orderitems = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findAllByUser_Id((user_id));

        orderitems = cartItems.stream()
                .map(cartItem -> mapFromCartitemtoOrderitem(cartItem))
                .collect(Collectors.toList());

        order.setOrderItems(orderitems);
        order.setUser(user);
        cartItemRepository.deleteAllById(Collections.singleton(user_id));
        return userRepository.save(user);
    }

}
