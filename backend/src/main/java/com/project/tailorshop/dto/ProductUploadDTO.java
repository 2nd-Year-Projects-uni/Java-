package com.project.tailorshop.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for uploading a product with an image file
 * Used when admin uploads product with image from frontend form
 */
public class ProductUploadDTO {
    private String name;
    private String description;
    private double price;
    private String category;
    private MultipartFile image;

    // Constructors
    public ProductUploadDTO() {}

    public ProductUploadDTO(String name, String description, double price, String category, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}
