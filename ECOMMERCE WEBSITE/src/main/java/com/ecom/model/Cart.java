package com.ecom.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(mappedBy = "cart")
    private User user;  // Associate the cart with a user
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartProduct> cartProducts = new HashSet<>();


    // Add a product to the cart, incrementing the quantity if it's already present
    public void addProduct(Product product) {
        CartProduct cartProduct = findCartProductByProduct(product);
        if (cartProduct != null) {
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);  // Increment quantity
        } else {
            cartProducts.add(new CartProduct(this, product, 1));  // Add new CartProduct
        }
    }
    // Find a CartProduct by the product
    public CartProduct findCartProductByProduct(Product product) {
        return cartProducts.stream()
            .filter(cartProduct -> cartProduct.getProduct().equals(product))
            .findFirst()
            .orElse(null);
    }
    
    // Remove a product from the cart
    public void removeProduct(Product product) {
        CartProduct cartProduct = findCartProductByProduct(product);
        if (cartProduct != null) {
            cartProducts.remove(cartProduct);
        }
    }
    
    // Get the total number of items in the cart
    public int getTotalItems() {
        return cartProducts.stream()
            .mapToInt(CartProduct::getQuantity)
            .sum();
    }

    // Get the total price of the cart
    public double getTotalPrice() {
        return cartProducts.stream()
            .mapToDouble(cartProduct -> cartProduct.getProduct().getPrice() * cartProduct.getQuantity())
            .sum();
    }


	/*
	 * public int getProductQuantity(Product product) { return (int)
	 * products.stream().filter(p -> p.equals(product)).count(); }
	 */

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
	public Set<CartProduct> getCartProducts() {
		return cartProducts;
	}
	public void setCartProducts(Set<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}
}
