package com.ecom.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    // Directory to upload images
    private final String UPLOAD_DIR = "src/main/resources/static/images/";

    // Admin home page
    @GetMapping("/")
    public String adminHome(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/details";
    }

    // Display form to create a new product
    @GetMapping("/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/CreateProduct";
    }

    // Handle form submission for creating a product
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) {
        try {
            // Check if an image file is uploaded
            if (!imageFile.isEmpty()) {
                // Absolute path to the upload directory
                String uploadDir = new File(UPLOAD_DIR).getAbsolutePath();
                File uploadDirectory = new File(uploadDir);

                // Create the directory if it doesn't exist
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                // Save the uploaded image with a unique name
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                File destFile = new File(uploadDir + File.separator + fileName);
                imageFile.transferTo(destFile);

                // Set the image filename in the product object
                product.setImageFileName(fileName);
            }
         // Handle stock field
            if (product.getStock() < 0) {
                model.addAttribute("errorMessage", "Stock quantity cannot be negative.");
                return "admin/CreateProduct";
            }

            // Save the product to the database
            productService.save(product);
            return "redirect:/admin/";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to save product. Please try again.");
            return "admin/CreateProduct";
        }
    }

    // Display form to edit an existing product
    @GetMapping("/edit")
    public String editProductForm(@RequestParam("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "admin/EditProduct";
        } else {
            return "redirect:/admin/";
        }
    }

    // Handle form submission for editing a product
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Product product,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) {
        try {
            if (!imageFile.isEmpty()) {
                // Absolute path to the upload directory
                String uploadDir = new File(UPLOAD_DIR).getAbsolutePath();

                // Save the new image with a unique name
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                File destFile = new File(uploadDir + File.separator + fileName);
                imageFile.transferTo(destFile);

                // Update the image filename in the product object
                product.setImageFileName(fileName);
            }
            // Handle stock field
            if (product.getStock() < 0) {
                model.addAttribute("errorMessage", "Stock quantity cannot be negative.");
                return "admin/EditProduct";
            }

            // Save updated product to the database
            productService.save(product);
            return "redirect:/admin/";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to update product. Please try again.");
            return "admin/EditProduct";
        }
    }

    // Delete a product
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") int id) {
        productService.deleteProductById(id);
        return "redirect:/admin/";
    }
}
