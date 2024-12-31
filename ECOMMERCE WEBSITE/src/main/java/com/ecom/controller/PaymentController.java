package com.ecom.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.service.CartService;
import com.ecom.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

	// Show payment details page with the cart items
	@GetMapping("/payment")
	public String showPaymentPage(Model model, HttpSession session) {
		// Fetch the user from the session
		User user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("errorMessage", "You need to be logged in to access the payment page.");
			return "redirect:/login"; // Redirect to login if user is not logged in
		}
	    System.out.println("User retrieved from session: " + user);


		// Fetch cart items from CartService for the logged-in user
		Map<Product, Integer> cartItems = cartService.getCartProducts(user); // Pass user to get the user's cart
	    System.out.println("Cart items for user: " + cartItems);
	    
	    double totalPrice = cartService.getTotalPrice(user);
	    System.out.println("Total price: " + totalPrice);


		if (cartItems == null || cartItems.isEmpty())  {
			model.addAttribute("errorMessage", "Your cart is empty.");
			return "redirect:/cart"; // Redirect back to cart if empty
		}

		// Add cart items and total price to the model
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", cartService.getTotalPrice(user)); // Pass user to calculate the correct total
																			// price

		return "payment"; // Show the payment page
	}
	
	
	@PostMapping("/processPayment")
	public String processPayment(
	        @RequestParam("paymentMethod") String paymentMethod,
	        @RequestParam(value = "cardNumber", required = false) String cardNumber,
	        @RequestParam(value = "expiryDate", required = false) String expiryDate,
	        @RequestParam(value = "cvv", required = false) String cvv,
	        @RequestParam(value = "productId", required = false) Long productId, // Optional for Buy Now
	        Model model, HttpSession session) {

	    // Fetch the logged-in user from the session
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	        model.addAttribute("errorMessage", "You need to be logged in to process payment.");
	        return "redirect:/login"; // Redirect to login if user is not logged in
	    }

	    Product product = null;
	    double totalPrice = 0;

	    // Handle payment for a single product (Buy Now)
	    if (productId != null) {
	        product = productService.getProductById(productId);
	        if (product == null) {
	            model.addAttribute("errorMessage", "Product not found or unavailable.");
	            return "redirect:/products"; // Redirect if product doesn't exist
	        }
	        totalPrice = product.getPrice(); // Use the product's price
	    } else {
	        // Handle payment for multiple products in the cart
	        Map<Product, Integer> cartItems = cartService.getCartProducts(user);
	        if (cartItems == null || cartItems.isEmpty()) {
	            model.addAttribute("errorMessage", "Your cart is empty.");
	            return "redirect:/cart"; // Redirect to cart if empty
	        }
	        totalPrice = cartService.getTotalPrice(user); // Calculate total price of cart items
	    }

	    // Simulate payment processing based on the selected payment method
	    if ("online".equals(paymentMethod)) {
	        // Validate online payment details
	        if (cardNumber == null || expiryDate == null || cvv == null) {
	            model.addAttribute("errorMessage", "Incomplete payment details.");
	            return "payment"; // Return to payment page if details are missing
	        }
	        System.out.println("Processing online payment with card: " + cardNumber);
	    } else if ("cod".equals(paymentMethod)) {
	        // Handle Cash on Delivery (COD)
	        System.out.println("Processing Cash on Delivery payment.");
	    } else {
	        model.addAttribute("errorMessage", "Invalid payment method selected.");
	        return "payment"; // Return to payment page for invalid payment method
	    }

	    // Payment successful: clear cart for the user (if not a Buy Now payment)
	    if (productId == null) {
	        cartService.clearCart(user); // Clear cart only for cart payments
	    }

	    // Pass success information to the model
	    model.addAttribute("totalPrice", totalPrice); // Add total price to model
	    model.addAttribute("message", "Payment Successful!"); // Add success message

	    return "success"; // Render success page
	}

	/*
	 * @PostMapping("/processPayment") public String
	 * processPayment(@RequestParam("paymentMethod") String paymentMethod,
	 * 
	 * @RequestParam(value = "cardNumber", required = false) String cardNumber,
	 * 
	 * @RequestParam(value = "expiryDate", required = false) String expiryDate,
	 * 
	 * @RequestParam(value = "cvv", required = false) String cvv, Model model,
	 * HttpSession session) {
	 * 
	 * // Fetch the user from session User user = (User)
	 * session.getAttribute("user"); if (user == null) {
	 * model.addAttribute("errorMessage",
	 * "You need to be logged in to process payment."); return "redirect:/login"; //
	 * Redirect to login if user is not logged in }
	 * System.out.println("Processing payment for user: " + user);
	 * 
	 * // Fetch the cart items for the logged-in user Map<Product, Integer>
	 * cartItems = cartService.getCartProducts(user);
	 * System.out.println("Cart items before processing payment: " + cartItems);
	 * 
	 * if (cartItems == null || cartItems.isEmpty()) {
	 * model.addAttribute("errorMessage", "Your cart is empty."); return
	 * "redirect:/cart"; // Redirect to cart if empty }
	 * 
	 * // Simulate payment processing based on the selected method if
	 * ("online".equals(paymentMethod)) { // Handle online payment logic (e.g.,
	 * validate card details) if (cardNumber == null || expiryDate == null || cvv ==
	 * null) { model.addAttribute("errorMessage", "Incomplete payment details.");
	 * return "payment"; // Return to the payment page if details are missing }
	 * System.out.println("Processing online payment with card: " + cardNumber); }
	 * else if ("cod".equals(paymentMethod)) { // Handle Cash on Delivery (COD)
	 * System.out.println("Processing COD payment."); } else {
	 * model.addAttribute("errorMessage", "Invalid payment method."); return
	 * "payment"; // Return to the payment page in case of an error }
	 * 
	 * // Calculate total price double totalPrice = cartService.getTotalPrice(user);
	 * // Pass user to get the correct total price
	 * 
	 * // If payment is successful, clear the cart and show success message
	 * cartService.clearCart(user); // Clear cart for the specific user after
	 * successful payment model.addAttribute("totalPrice", totalPrice); // Add total
	 * price to model model.addAttribute("message", "Payment Successful!"); // Add
	 * success message to model
	 * 
	 * return "success"; // Redirect to success page }
	 */
}