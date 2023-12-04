package com.springapps.shop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Orderitem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonBackReference("orderitem-product")
    private Product product;

    @ManyToOne
    @JoinColumn(name="order_id")
    @JsonBackReference("orderitem-order")
    private Order order;



    public Orderitem() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setUser(Order order) {
        this.order = order;
    }
}
