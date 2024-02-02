package com.springapps.shop.services;

import com.springapps.shop.dtos.CartItemResponseDTO;
import com.springapps.shop.dtos.CartResponseDTO;
import com.springapps.shop.dtos.OrderItemResponseDTO;
import com.springapps.shop.dtos.OrderResponseDTO;
import com.springapps.shop.entities.CartItem;
import com.springapps.shop.entities.Order;
import com.springapps.shop.entities.Orderitem;
import com.springapps.shop.entities.User;
import com.springapps.shop.exceptions.ResourceNotFoundException;
import com.springapps.shop.repositories.CartItemRepository;
import com.springapps.shop.repositories.OrderRepository;
import com.springapps.shop.repositories.OrderitemRepository;
import com.springapps.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public Double computeTotalPrice(List<Orderitem> orderitems) {
        Optional<Double> totalPrice = orderitems.stream()
                .map(orderitem -> orderitem.getPrice())
                .reduce((sum, number) -> sum + number);

        return totalPrice.orElseThrow(() -> new ResourceNotFoundException("total price could not be computed"));

    }

    public Orderitem mapFromCartitemtoOrderitem(CartItem cartItem, Order order) {
        Orderitem orderitem = new Orderitem();
        orderitem.setQuantity(cartItem.getQuantity());
        orderitem.setProduct(cartItem.getProduct());
        orderitem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        orderitem.setOrder(order);
        return orderitem;
    }

    //Plasam o comanda pentru un utilizator (cu produsele pe care le are in cosul de cumparaturi)
    //
    //Endpoint: /orders/add/{userId}
    @Transactional
    public Order addOrderToUser() {
        //gasesc userul dupa id
        //creez o noua comanda
        //atasez comanda de utilizator
        //gasesc cartitem-urile utilizatorului dupa id
        //mut cartitem-urile in orderitem , sau le mapez
        // atasez lista de orderitem la order
        //salvez orderul
        //sterg lista de cartitem-uri dupa id utilizator
        //salvez user-ul

        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(loggedInUserName).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Order order = new Order();

        List<CartItem> cartItems = cartItemRepository.findAllByUser_Id(user.getId());
        if (cartItems.size()==0){
            throw new ResourceNotFoundException("order cannot be placed. Cart is empty");
        }
        List<Orderitem> orderitems = cartItems.stream()
                .map(cartItem -> mapFromCartitemtoOrderitem(cartItem,order))
                .collect(Collectors.toList());
        order.setTotalPrice(computeTotalPrice(orderitems));
        order.setOrderItems(orderitems);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        cartItemRepository.deleteAllByUser_Id(user.getId());
        return orderRepository.save(order);
    }

    @Transactional
    public List<Order> viewOrders(Long userId) {
        List<Order> allOrders = orderRepository.findAllByUser_IdOrderByCreatedAt(userId);
        return allOrders;
    }


}
