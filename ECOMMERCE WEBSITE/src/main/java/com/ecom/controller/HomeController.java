package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecom.model.Product;
import com.ecom.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

	/*
	 * @GetMapping("/products") public String productPage(Model model) {
	 * model.addAttribute("products", productService.getAllProducts()); return
	 * "products"; }
	 */

    @GetMapping("/products")
    public String productPage(Model model) {
        System.out.println("Entering /products mapping");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            model.addAttribute("message", "No products found in the database.");
        } else {
            model.addAttribute("products", products);
        }
        return "products";
    }
    

    @GetMapping("/productDetails/{productId}")
    public String viewProductDetails(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProductById(productId); // Fetch product details
        if (product == null) {
            model.addAttribute("errorMessage", "Product not found.");
            return "redirect:/products"; // Redirect to product listing if not found
        }

        boolean isInStock = product.getStock() > 0; // Check stock status
        model.addAttribute("product", product); // Add product details to the model
        model.addAttribute("isInStock", isInStock); // Add stock status to the model
        return "product-details"; // Render the product details page
    }
}
