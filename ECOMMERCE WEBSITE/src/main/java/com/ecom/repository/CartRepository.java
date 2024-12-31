package com.ecom.repository;

import com.ecom.model.Cart;
import com.ecom.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);  // Query method to find cart by user
    
 // Custom save method for Cart
    @Query("UPDATE Cart c SET c.user = :user WHERE c.id = :cartId")
    @Modifying
    void saveCartForUser(@Param("cartId") Long cartId, @Param("user") User user);
}
