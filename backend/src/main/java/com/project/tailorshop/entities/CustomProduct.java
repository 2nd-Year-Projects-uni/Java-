package com.project.tailorshop.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "custom_products")
public class CustomProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String productType;
    private String imageUrl;
    private int stock = 100;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private Double chest;
    private Double waist;
    private Double length;
    private Double inseam;
    private Double sleeve;

    public CustomProduct() {
        this.createdDate = LocalDateTime.now();
        this.stock = 100;
    }

    public CustomProduct(String name, String description, double price, String productType, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.imageUrl = imageUrl;
        this.stock = 100;
        this.createdDate = LocalDateTime.now();
    }

    public CustomProduct(String name, double price, String productType, String imageUrl) {
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.imageUrl = imageUrl;
        this.stock = 100;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Double getChest() { return chest; }
    public void setChest(Double chest) { this.chest = chest; }

    public Double getWaist() { return waist; }
    public void setWaist(Double waist) { this.waist = waist; }

    public Double getLength() { return length; }
    public void setLength(Double length) { this.length = length; }

    public Double getInseam() { return inseam; }
    public void setInseam(Double inseam) { this.inseam = inseam; }

    public Double getSleeve() { return sleeve; }
    public void setSleeve(Double sleeve) { this.sleeve = sleeve; }
}
