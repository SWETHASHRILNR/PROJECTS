package com.ecom.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Cart;
import com.ecom.model.CartProduct;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addProductToCart(Product product, User user) {
        // Fetch the Product entity
        Product managedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Fetch the User's cart (from DB)
        Cart userCart = user.getCart();
        if (userCart == null) {
            userCart = new Cart(); // Initialize a new Cart if none exists
            user.setCart(userCart);
            cartRepository.save(userCart); // Persist the new Cart
        }

        // Check if the product is already in the cart
        Optional<CartProduct> existingCartProduct = userCart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().equals(managedProduct))
                .findFirst();

        if (existingCartProduct.isPresent()) {
            // If product is already in the cart, update the quantity
            CartProduct cartProduct = existingCartProduct.get();
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
        } else {
            // If product is not in the cart, add it
            CartProduct newCartProduct = new CartProduct(userCart, managedProduct, 1);
            userCart.getCartProducts().add(newCartProduct);
        }

        cartRepository.save(userCart); // Persist the cart changes
    }

    @Override
    @Transactional
    public void removeProductFromCart(Product product, User user) {
        Cart userCart = user.getCart();
        if (userCart != null) {
            userCart.getCartProducts().removeIf(cartProduct -> cartProduct.getProduct().equals(product));
            cartRepository.save(userCart);
        }
    }

    @Override
    @Transactional
    public void loadUserCart(User user) {
        if (user == null) {
            return; // Handle the case when the user is null
        }

        Cart userCart = user.getCart();
        if (userCart == null) {
            userCart = new Cart(); // Initialize a new Cart if none exists
            user.setCart(userCart);
            cartRepository.save(userCart); // Optionally persist the new Cart
        }

        // Ensure the products collection is loaded if it's lazily loaded
        Hibernate.initialize(userCart.getCartProducts());
    }

    @Override
    @Transactional
    public void saveUserCart(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Retrieve or create the user's cart
        Cart userCart = user.getCart();
        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(user);
            user.setCart(userCart);
        }

        // Save the updated cart to the database
        cartRepository.save(userCart);
    }
    
    @Override
    public double getTotalPrice(User user) {
        Cart userCart = user.getCart();
        if (userCart == null) {
            return 0;
        }

        // Debugging individual price calculation
        double total = userCart.getCartProducts().stream()
                .mapToDouble(cartProduct -> {
                    double price = cartProduct.getProduct().getPrice();
                    int quantity = cartProduct.getQuantity();
                    System.out.println("Product: " + cartProduct.getProduct().getName() + ", Price: " + price + ", Quantity: " + quantity);
                    return price * quantity;
                })
                .sum();

        System.out.println("Total Price: " + total);
        return total;
    }



    public Map<Product, Integer> getCartProducts(User user) {
        Map<Product, Integer> cartProducts = new HashMap<>();
        try {
            Cart cart = user.getCart();
            if (cart != null) {
                for (CartProduct cartProduct : cart.getCartProducts()) {
                    System.out.println("Cart Product: " + cartProduct.getProduct().getName() + ", Quantity: " + cartProduct.getQuantity());
                    cartProducts.put(cartProduct.getProduct(), cartProduct.getQuantity());
                }
            }
        } catch (Exception e) {
            // Log the error or handle it as needed
            System.err.println("Error getting cart products: " + e.getMessage());
        }
        return cartProducts;
    }


    @Override
    public int getCartItemCount(User user) {
        Cart userCart = user.getCart();
        if (userCart == null) {
            return 0;
        }
        return userCart.getCartProducts().stream().mapToInt(CartProduct::getQuantity).sum();
    }

    @Override
    @Transactional
    public void updateProductQuantity(Product product, int quantity, User user) {
        // Check if the quantity is <= 0, in which case remove the product
        if (quantity <= 0) {
            removeProductFromCart(product, user);  // Remove product if quantity is <= 0
        } else {
            Cart userCart = user.getCart();
            if (userCart != null) {
                // Initialize the cart's products collection if necessary
                Hibernate.initialize(userCart.getCartProducts());

                // Check if the product exists in the cart and update the quantity
                Optional<CartProduct> existingCartProduct = userCart.getCartProducts().stream()
                        .filter(cartProduct -> cartProduct.getProduct().equals(product))
                        .findFirst();

                existingCartProduct.ifPresent(cartProduct -> {
                    cartProduct.setQuantity(quantity);  // Update quantity
                    // Ensure that the cart and cartProduct are saved in the DB
                    cartRepository.save(userCart);  // Persist the cart after modification
                });
            }
        }
    }


    @Override
    public double getTotalPrice() {
        return 0;  // Not needed as it's already implemented for user-specific total price
    }

    @Override
    public void clearCart(User user) {
        Cart userCart = user.getCart();
        if (userCart != null) {
            userCart.getCartProducts().clear();
			/*
			 * // Optional: If you want to completely remove the cart (delete it), // you
			 * can uncomment the following line. cartRepository.delete(userCart);
			 */
            cartRepository.save(userCart);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
