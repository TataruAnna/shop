package com.springapps.shop.services;

import com.springapps.shop.dtos.CartItemResponseDTO;
import com.springapps.shop.dtos.CartRequestDTO;
import com.springapps.shop.dtos.CartResponseDTO;
import com.springapps.shop.dtos.mapper.CartItemMapper;
import com.springapps.shop.entities.CartItem;
import com.springapps.shop.entities.Product;
import com.springapps.shop.entities.User;
import com.springapps.shop.exceptions.ResourceNotFoundException;
import com.springapps.shop.repositories.CartItemRepository;
import com.springapps.shop.repositories.ProductRepository;
import com.springapps.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {


    private CartItemRepository cartItemRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private CartItemMapper cartItemMapper;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Transactional
    public CartItem addToCart(CartRequestDTO cartRequestDTO) {
        User user = userRepository.findById(cartRequestDTO.getUserId()).orElseThrow(()->new ResourceNotFoundException("user not found"));
        Product product = productRepository.findById(cartRequestDTO.getProductId()).orElseThrow(()->new ResourceNotFoundException("product not found"));
        if (product.getStock()<cartRequestDTO.getQuantity()|| product.getStock()==null){
            throw  new ResourceNotFoundException("out of stock");
        }



        CartItem cartItem =cartItemMapper.mapCartRequestDTOtoCartItem(cartRequestDTO, product, user);
//        CartItem cartItem = new CartItem();
//        cartItem.setProduct(product);
//        cartItem.setUser(user);
//        cartItem.setQuantity(cartRequestDTO.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    public CartResponseDTO viewCart(Long userId){
        List<CartItem> cartItems = cartItemRepository.findAllByUser_Id(userId);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setTotalPrice(computeTotalPrice(cartItems));
        cartResponseDTO.setCartItemResponseDTOS(getCartItemResponseDTOS(cartItems));
        return cartResponseDTO;
    }

    private List<CartItemResponseDTO> getCartItemResponseDTOS(List<CartItem> cartItems) {
        List<CartItemResponseDTO> cartItemResponseDTOS = cartItems.stream()
                .map(cartItem -> cartItemMapper.mapCartItemToCartItemDTO(cartItem))
                .collect(Collectors.toList());
        return cartItemResponseDTOS;
    }

    public Double computeTotalPrice(List<CartItem> cartItems){
        Optional<Double> totalPrice = cartItems.stream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
                .reduce((sum, number)->sum+number);

        return totalPrice.orElseThrow(()-> new ResourceNotFoundException("total price could not be computed"));

    }

    public CartItemResponseDTO mapCartItemToCartItemResponseDTO (CartItem cartItem){
        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
        cartItemResponseDTO.setId(cartItem.getId());
        cartItemResponseDTO.setProductId(cartItem.getProduct().getId());
        cartItemResponseDTO.setProductName(cartItem.getProduct().getName());
        cartItemResponseDTO.setPrice(cartItem.getProduct().getPrice());
        cartItemResponseDTO.setQuantity(cartItem.getQuantity());
        return cartItemResponseDTO;
    }
}
