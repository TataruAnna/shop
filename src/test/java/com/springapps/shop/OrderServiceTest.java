package com.springapps.shop;


import com.springapps.shop.entities.*;
import com.springapps.shop.exceptions.ResourceNotFoundException;
import com.springapps.shop.repositories.CartItemRepository;
import com.springapps.shop.repositories.OrderRepository;
import com.springapps.shop.repositories.UserRepository;
import com.springapps.shop.services.OrderService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    @Spy
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;




    @Test
    public void testAddOrderToUser_ShouldThrowEXception(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("Gigel");

        SecurityContextHolder.setContext(securityContext);
        User user = new User();
        user.setUsername("Gigel");
        user.setPassword("pass");
        user.setId(2L);




        when(userRepository.findUserByUsername("Gigel")).thenReturn(Optional.of(user));
        when(cartItemRepository.findAllByUser_Id(2L)).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.addOrderToUser();
        });
    }
    @Test
    public void testAddOrderToUser_ShouldRetunSavedOrder(){
        //given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("Gigel");

        SecurityContextHolder.setContext(securityContext);
        User user = new User();
        user.setUsername("Gigel");
        user.setPassword("pass");
        user.setId(2L);

        CartItem cartItem1 = new CartItem();
        cartItem1.setId(3L);

        CartItem cartItem2 = new CartItem();
        cartItem1.setId(4L);

        Orderitem orderItem1 = new Orderitem();
        orderItem1.setId(5L);

        Orderitem orderItem2 = new Orderitem();
        orderItem1.setId(6L);


        List<CartItem> cartItemList = Arrays.asList(cartItem1,cartItem2);
        List<Orderitem> orderitemList = Arrays.asList(orderItem1,orderItem2);



        when(userRepository.findUserByUsername("Gigel")).thenReturn(Optional.of(user));
        when(cartItemRepository.findAllByUser_Id(2L)).thenReturn(cartItemList);
        when(orderService.computeTotalPrice(orderitemList)).thenReturn(100.0);
        when(orderService.getOrderitems(any(Order.class), any(ArrayList.class))).thenReturn(orderitemList);

        doNothing().when(cartItemRepository).deleteAllByUser_Id(2L);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when

        Order resultOrder = orderService.addOrderToUser();


        //then

        assertEquals(100.00,resultOrder.getTotalPrice());
        assertEquals(user,resultOrder.getUser());

    }
}
