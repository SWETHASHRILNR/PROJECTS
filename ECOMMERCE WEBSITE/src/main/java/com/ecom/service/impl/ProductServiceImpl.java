package com.ecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

	/*
	 * @Override public List<Product> getAllProducts() { return
	 * productRepository.findAll(); }
	 */
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Products: " + products); // Log the list of products
        return products;
    }

	/*
	 * @Override public Product getProductById(Long id) { Optional<Product>
	 * productOptional = productRepository.findById(id); return
	 * productOptional.orElse(null); }
	 */
    @Override
    public Product getProductById(Long id) {
        System.out.println("Fetching product with ID: " + id);  // Log the ID being fetched
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            System.out.println("Product with ID " + id + " not found.");
        } else {
            System.out.println("Product with ID " + id + " found: " + productOptional.get().getName());
        }
        
        return productOptional.orElse(null);  // Return the product or null if not found
    }




    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
    
    
 // Add a method to check stock for a specific product
    @Override
    public boolean checkStockAvailability(Long productId, int quantity) {
        Product product = getProductById(productId);
        return product != null && product.getStock() >= quantity;
    }

    // Add a method to update stock when adding to cart or making a purchase
    @Override
    public void updateStock(Long productId, int quantity) {
        Product product = getProductById(productId);
        if (product != null && product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);  // Save the updated product
        } else {
            throw new RuntimeException("Not enough stock available for product with ID " + productId);
        }
    }

}
