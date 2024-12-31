package com.ecom.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	public String name;
	public String description;
	public double price;
	private int stock;
	public String imageFileName;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

    public Product(String name, String description, double price, int stock, String imageFileName) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.imageFileName = imageFileName;
	}
    
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock="
				+ stock + ", imageFileName=" + imageFileName + "]";
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	// Override equals() and hashCode() for correct comparisons in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id); // Compare based on ID
        //return id == product.id;  // Compare based on ID since it is unique
    }


	@Override
    public int hashCode() {
        return Objects.hash(id);  // Use ID for hashcode generation
    }

	
}
