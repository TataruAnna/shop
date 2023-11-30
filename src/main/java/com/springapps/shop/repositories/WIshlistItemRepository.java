package com.springapps.shop.repositories;

import com.springapps.shop.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WIshlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
