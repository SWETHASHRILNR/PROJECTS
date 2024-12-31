package com.ecom.service.impl;

import com.ecom.model.Cart;
import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;
import com.ecom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartService cartService;  // If you have a CartService for cart operations

    // Create a new user with a cart
    public User createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // Store plain text (or hashed in real-world apps)
        user.setEmail(email);
        
        // Create a new cart for the user
        Cart cart = new Cart();
        user.setCart(cart);  // Link the new cart to the user
        
        return userRepository.save(user);  // Save the user (and the associated cart)
    }

    // Register a new user
    public User registerUser(User user) {
        // Ensure that the username is not already taken
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists.");
        }
        return userRepository.save(user);
    }

    // Authenticate a user by checking if the username and password match
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // User is authenticated
        }
        throw new RuntimeException("Invalid username or password"); // Invalid credentials
    }
}
