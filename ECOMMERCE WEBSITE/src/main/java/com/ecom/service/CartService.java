package com.ecom.service;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.User;

import java.util.List;
import java.util.Map;

public interface CartService {

	void addProductToCart(Product product, User user);

	void removeProductFromCart(Product product, User user);

	void loadUserCart(User user);

	void saveUserCart(User user);

	double getTotalPrice(User user);

	Map<Product, Integer> getCartProducts(User user);

	int getCartItemCount(User user);

	void updateProductQuantity(Product product, int quantity, User user);

	double getTotalPrice();

	void clearCart(User user);

	Product getProductById(Long productId);





}
