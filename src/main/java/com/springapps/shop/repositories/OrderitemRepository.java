package com.springapps.shop.repositories;

import com.springapps.shop.entities.CartItem;
import com.springapps.shop.entities.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderitemRepository extends JpaRepository<Orderitem,Long> {



}
