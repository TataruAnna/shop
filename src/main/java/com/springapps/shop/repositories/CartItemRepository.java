package com.springapps.shop.repositories;

import com.springapps.shop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUser_Id (Long userId);

    void deleteAllByUser_Id(Long userId);



}
