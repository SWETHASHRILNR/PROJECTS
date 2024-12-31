package com.ecom.service;

import com.ecom.model.Product;

import java.util.List;

public interface ProductService {
    
    void save(Product product);  // Save a product

    List<Product> getAllProducts();  // Get all products

    Product getProductById(Long id);  // Get a product by its ID

    List<Product> findAll();  // Get all products (move this here from implementation)

    void deleteProductById(int id);  // Delete a product by ID

	boolean checkStockAvailability(Long productId, int quantity);

	void updateStock(Long productId, int quantity);

}
