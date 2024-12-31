package com.ecom.controller;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.ProductRepository;
import com.ecom.service.CartService;
import com.ecom.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    // Helper method to check if the user is logged in and retrieve the cart items
    private User getLoggedInUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorMessage", "You need to be logged in to access this page.");
            return null;  // Return null if the user is not logged in
        }
        cartService.loadUserCart(user);  // Load the user's cart
        return user;
    }

 // Helper method to update the cart in session and model
    private void updateCartInSessionAndModel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");  // Get the logged-in user from the session
        if (user != null) {
            int cartItemCount = cartService.getCartItemCount(user);  // Get updated cart item count for the user
            if (cartItemCount == 0) {
                cartItemCount = 0;  // Ensure cart item count is 0 if cart is empty
            }
            session.setAttribute("cartItemCount", cartItemCount);  // Update session with the item count
            model.addAttribute("cartItemCount", cartItemCount);  // Add cart item count to the model
            model.addAttribute("cartItems", cartService.getCartProducts(user));  // Add cart products to the model
        } else {
            // Handle the case if the user is not logged in
            session.setAttribute("cartItemCount", 0);  // Set count to 0 for unauthenticated users
            model.addAttribute("cartItemCount", 0);  // Add cart item count as 0 to the model
            model.addAttribute("cartItems", new HashMap<>());  // Add an empty cart to the model
        }
    }


 // Add to Cart functionality
    @GetMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId, Model model, HttpSession session) {
        User user = getLoggedInUser(session, model);  // Use the helper method
        if (user == null) {
            return "redirect:/login";  // Redirect to login if user is not logged in
        }

        Product product = productService.getProductById(productId);
        if (product != null) {
            int stock = productRepository.findStockById(productId);  // Get stock of the product
            if (stock > 0) {
                Map<Product, Integer> cartProducts = cartService.getCartProducts(user);
                System.out.println("Current cart products: " + cartProducts);  // Debugging cart products

                if (cartProducts.containsKey(product)) {
                    // Check if enough stock is available for updating quantity
                    int currentQuantity = cartProducts.get(product);
                    if (currentQuantity < stock) {
                        cartService.updateProductQuantity(product, currentQuantity + 1, user);
                        model.addAttribute("message", "Quantity updated in cart!");
                    } else {
                        model.addAttribute("errorMessage", "Not enough stock available to add more.");
                    }
                } else {
                    cartService.addProductToCart(product, user);  // Add product to cart
                    model.addAttribute("message", "Product added to cart!");
                }
                // Recalculate total price after cart modification
                double totalPrice = cartService.getTotalPrice(user);
                model.addAttribute("totalPrice", totalPrice);  // Add the updated total price to the model

                updateCartInSessionAndModel(session, model);  // Update session and model after modification
            } else {
                model.addAttribute("errorMessage", "Product is out of stock.");
            }
        } else {
            model.addAttribute("errorMessage", "Product not found.");
        }

        return "redirect:/cart";  // Redirect to cart page
    }

 // Buy Now functionality
    @GetMapping("/buyNow/{productId}")
    public String buyNow(@PathVariable Long productId, Model model, HttpSession session) {
        User user = getLoggedInUser(session, model); // Retrieve logged-in user
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }

        // Fetch product details
        Product product = productService.getProductById(productId);
        if (product == null) {
            model.addAttribute("errorMessage", "Product not found or unavailable.");
            return "redirect:/products"; // Redirect if product doesn't exist
        }

        // Check stock availability
        int stock = productRepository.findStockById(productId);
        if (stock <= 0) {
            model.addAttribute("errorMessage", "Product is out of stock.");
            return "redirect:/products"; // Redirect if out of stock
        }

        // Calculate total price for the selected product
        double totalPrice = product.getPrice();

        // Pass product and total price to the model
        model.addAttribute("product", product);
        model.addAttribute("totalPrice", totalPrice);

        // Render the payment page
        return "payment";
    }



    // Show the cart page
    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        User user = getLoggedInUser(session, model);  // Use the helper method
        if (user == null) {
            return "redirect:/login";  // Redirect to login if user is not logged in
        }

        // Get the cart items
        Map<Product, Integer> cartItems = cartService.getCartProducts(user);
        
        // Remove null products if any
        cartItems.entrySet().removeIf(entry -> entry.getKey() == null);

        // Calculate the total price of the cart
        double totalPrice = cartService.getTotalPrice(user);  // Pass the user to ensure correct price calculation

        // Update the cart item count
        int cartItemCount = cartItems.size();  // Get the actual count of items in the cart
        session.setAttribute("cartItemCount", cartItemCount);  // Update session

        // Add attributes to the model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItemCount", cartItemCount);

        return "cart";  // Render the cart page
    }


    // Remove product from cart
    @PostMapping("/removeFromCart/{productId}")
    public String removeFromCart(@PathVariable Long productId, Model model, HttpSession session) {
        User user = getLoggedInUser(session, model);  // Use the helper method
        if (user == null) {
            return "redirect:/login";  // Redirect to login if user is not logged in
        }

        Product product = productService.getProductById(productId);
        if (product != null) {
            cartService.removeProductFromCart(product, user);  // Remove product from cart
            updateCartInSessionAndModel(session, model);  // Update session and model
            model.addAttribute("message", "Product removed from cart!");
        } else {
            model.addAttribute("errorMessage", "Product not found.");
        }

        return "redirect:/cart";  // Redirect to cart page
    }

    // Update product quantity in cart
    @PostMapping("/updateQuantity/{productId}/{quantity}")
    public String updateQuantity(@PathVariable Long productId, @PathVariable int quantity, Model model, HttpSession session) {
        User user = getLoggedInUser(session, model);  // Use the helper method
        if (user == null) {
            return "redirect:/login";  // Redirect to login if user is not logged in
        }

        if (quantity < 1) {
            model.addAttribute("errorMessage", "Quantity must be at least 1.");
            return "redirect:/cart";  // Don't allow quantity less than 1
        }

        Product product = productService.getProductById(productId);
        if (product != null) {
            int stock = productRepository.findStockById(productId);
            if (quantity <= stock) {
                cartService.updateProductQuantity(product, quantity, user);  // Update product quantity
                updateCartInSessionAndModel(session, model);  // Update session and model
                model.addAttribute("message", "Product quantity updated!");
            } else {
                model.addAttribute("errorMessage", "Not enough stock to update quantity.");
            }
        } else {
            model.addAttribute("errorMessage", "Product not found.");
        }

        return "redirect:/cart";  // Redirect to cart page
    }

    // Proceed to checkout
    @GetMapping("/checkout")
    public String proceedToCheckout(Model model, HttpSession session) {
        User user = getLoggedInUser(session, model);  // Use the helper method
        if (user == null) {
            return "redirect:/login";  // Redirect to login if user is not logged in
        }

        Map<Product, Integer> cartItems = cartService.getCartProducts(user);  // Get the entire cart
        if (cartItems != null && !cartItems.isEmpty()) {
            double totalPrice = cartService.getTotalPrice(user);  // Get total price of the cart
            model.addAttribute("cartItems", cartItems);  // Add cart items to the model
            model.addAttribute("totalPrice", totalPrice);  // Add total price to the model

            // Clear the user's cart after checkout
            //cartService.clearCart(user);  // Empty the cart
            //model.addAttribute("message", "Proceeding to checkout.");
            return "checkout";
            //return "redirect:/payment";  // Proceed to the checkout page
        } else {
            model.addAttribute("errorMessage", "Your cart is empty.");
            return "redirect:/cart";  // Redirect to cart if it's empty
        }
    }
    
    @PostMapping("/confirm-payment")
    public String confirmPayment(HttpSession session, Model model) {
        User user = getLoggedInUser(session, model); // Use the helper method
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }

        cartService.clearCart(user); // Empty the cart after payment confirmation
        model.addAttribute("message", "Payment successful! Thank you for your order.");
        return "payment-success"; // Redirect to a payment success page
    }

}
