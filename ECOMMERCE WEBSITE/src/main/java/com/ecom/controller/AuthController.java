package com.ecom.controller;

import com.ecom.model.User;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show the registration page
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // The view that shows registration form
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String password, 
                               @RequestParam String email, 
                               Model model) {
        try {
            // Use createUser method to register the user with a cart
            User newUser = userService.createUser(username, password, email);
            model.addAttribute("message", "User registered successfully! Please log in.");
            return "login"; // Redirect to login page after successful registration
        } catch (RuntimeException e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register"; // Stay on registration page in case of error
        }
    }

    // Show the login page
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // The view that shows login form
    }

    // Handle user login
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, 
                            @RequestParam String password, 
                            Model model, 
                            HttpSession session) {
        try {
            User user = userService.authenticate(username, password);
            session.setAttribute("user", user); // Store user in session
            return "redirect:/"; // Redirect to home page after successful login
        } catch (RuntimeException e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            return "login"; // Stay on login page in case of error
        }
    }

    // Handle user logout
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Invalidate the session to log out
        return "redirect:/"; // Redirect to home page after logout
    }
}
