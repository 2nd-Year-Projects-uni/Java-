package com.project.tailorshop.controllers;

import com.project.tailorshop.dto.ApiResponse;
import com.project.tailorshop.dto.ProductUploadDTO;
import com.project.tailorshop.entities.Product;
import com.project.tailorshop.repositories.ProductRepository;
import com.project.tailorshop.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductController(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    /**
     * GET all products
     * @return List of all products with their image URLs
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Products retrieved successfully", products));
    }

    /**
     * GET products by category
     * @param category Category name
     * @return List of products in the specified category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategory(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products retrieved successfully", products));
    }

    /**
     * GET single product by ID
     * @param id Product ID
     * @return Product details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Product retrieved successfully", product.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Product not found", null));
        }
    }

    /**
     * POST - Upload a new product with image
     * Uses multipart/form-data for file upload
     * @param name Product name
     * @param description Product description
     * @param price Product price
     * @param category Product category
     * @param image Image file
     * @return Saved product with image URL
     */
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Product>> uploadProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String category,
            @RequestParam(required = false) MultipartFile image) {

        try {
            // Validate required fields
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Product name is required", null));
            }

            if (description == null || description.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Product description is required", null));
            }

            if (price <= 0) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Product price must be greater than 0", null));
            }

            if (category == null || category.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Product category is required", null));
            }

            // Save image if provided
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.saveProductImage(image);
            }

            // Create and save product
            Product product = new Product(name, description, price, category, imageUrl);
            Product savedProduct = productRepository.save(product);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Product uploaded successfully", savedProduct));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error saving image: " + e.getMessage(), null));
        }
    }

    /**
     * PUT - Update an existing product
     * @param id Product ID
     * @param name Updated product name
     * @param description Updated product description
     * @param price Updated product price
     * @param category Updated product category
     * @param image New image file (optional)
     * @return Updated product
     */
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam String category,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) MultipartFile image) {

        try {
            Optional<Product> existingProduct = productRepository.findById(id);
            if (!existingProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Product not found", null));
            }

            Product product = existingProduct.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            if (stock != null) {
                product.setStock(stock);
            }

            // Update image if a new one is provided
            if (image != null && !image.isEmpty()) {
                // Delete old image
                if (product.getImageUrl() != null) {
                    imageService.deleteProductImage(product.getImageUrl());
                }
                // Save new image
                String imageUrl = imageService.saveProductImage(image);
                product.setImageUrl(imageUrl);
            }

            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", updatedProduct));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error saving image: " + e.getMessage(), null));
        }
    }

    /**
     * DELETE - Delete a product and its image
     * @param id Product ID
     * @return Success/failure message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Product not found", null));
        }

        // Delete image if it exists
        if (product.get().getImageUrl() != null) {
            imageService.deleteProductImage(product.get().getImageUrl());
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted successfully", null));
    }
}
