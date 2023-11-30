package com.springapps.shop.dtos;

import java.util.List;

public class CartResponseDTO {

    private List<CartItemResponseDTO> cartItemResponseDTOS;
    private Double totalPrice;

    public CartResponseDTO() {
    }

    public CartResponseDTO(List<CartItemResponseDTO> cartItemResponseDTOS, Double totalPrice) {
        this.cartItemResponseDTOS = cartItemResponseDTOS;
        this.totalPrice = totalPrice;
    }

    public List<CartItemResponseDTO> getCartItemResponseDTOS() {
        return cartItemResponseDTOS;
    }

    public void setCartItemResponseDTOS(List<CartItemResponseDTO> cartItemResponseDTOS) {
        this.cartItemResponseDTOS = cartItemResponseDTOS;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
