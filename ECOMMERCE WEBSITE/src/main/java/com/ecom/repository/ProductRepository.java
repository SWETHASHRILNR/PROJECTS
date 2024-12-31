package com.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Optional<Product> findById(Long id);

    // Custom query to get the stock based on the product ID
	/*
	 * @Query("SELECT p.stock FROM Product p WHERE p.id = :id") int
	 * findStockById(Long id);
	 */
    
    @Query("SELECT p.stock FROM Product p WHERE p.id = :productId")
    int findStockById(@Param("productId") Long productId);

}
