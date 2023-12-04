package com.springapps.shop.dtos;

public class OrderItemResponseDTO {

    private Long id;
    private Long productId;
    private Long orderId;
    private String productName;
    private Integer quantity;
    private Double price;

    public OrderItemResponseDTO() {
    }

    public OrderItemResponseDTO(Long id, Long productId, Long orderId, String productName, Integer quantity, Double price) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
