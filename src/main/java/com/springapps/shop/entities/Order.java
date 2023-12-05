package com.springapps.shop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("order-user")
    private User user;

    @OneToMany(mappedBy = "order",  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference("orderitem-order")
    private List<Orderitem> orderItems;

    public Order() {
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Orderitem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Orderitem> orderItems) {
        this.orderItems = orderItems;
    }
}
