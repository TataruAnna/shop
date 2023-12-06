package com.springapps.shop.repositories;

import com.springapps.shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUser_IdOrderByCreatedAt(Long userId);
}
