package com.springapps.shop.dtos;

import java.util.List;

public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private List<OrderItemResponseDTO> orderItemResponseDTOS;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Long id, Long userId, List<OrderItemResponseDTO> orderItemResponseDTOS) {
        this.id = id;
        this.userId = userId;
        this.orderItemResponseDTOS = orderItemResponseDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemResponseDTO> getOrderItemResponseDTOS() {
        return orderItemResponseDTOS;
    }

    public void setOrderItemResponseDTOS(List<OrderItemResponseDTO> orderItemResponseDTOS) {
        this.orderItemResponseDTOS = orderItemResponseDTOS;
    }
}
