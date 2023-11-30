package com.springapps.shop.repositories;

import com.springapps.shop.entities.Whishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Whishlist, Long> {
}
