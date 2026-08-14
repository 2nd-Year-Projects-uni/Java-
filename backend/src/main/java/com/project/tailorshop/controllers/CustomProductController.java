package com.project.tailorshop.controllers;

import com.project.tailorshop.dto.ApiResponse;
import com.project.tailorshop.entities.CustomProduct;
import com.project.tailorshop.repositories.CustomProductRepository;
import com.project.tailorshop.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/custom-products")
public class CustomProductController {

    private final CustomProductRepository customProductRepository;
    private final ImageService imageService;

    public CustomProductController(CustomProductRepository customProductRepository, ImageService imageService) {
        this.customProductRepository = customProductRepository;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomProduct>>> getAllCustomProducts() {
        List<CustomProduct> products = customProductRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Custom products retrieved", products));
    }

    @GetMapping("/type/{productType}")
    public ResponseEntity<ApiResponse<List<CustomProduct>>> getByProductType(@PathVariable String productType) {
        List<CustomProduct> products = customProductRepository.findByProductType(productType);
        return ResponseEntity.ok(new ApiResponse<>(true, "Custom products retrieved", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomProduct>> getCustomProductById(@PathVariable Long id) {
        Optional<CustomProduct> product = customProductRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Custom product retrieved", product.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Custom product not found", null));
        }
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<CustomProduct>> uploadCustomProduct(
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "100") Integer stock,
            @RequestParam(required = false) Double chest,
            @RequestParam(required = false) Double waist,
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double inseam,
            @RequestParam(required = false) Double sleeve,
            @RequestParam(required = false) MultipartFile image) {

        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Product name is required", null));
            }

            if (price <= 0) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Product price must be greater than 0", null));
            }

            String type = (productType != null && !productType.trim().isEmpty()) ? productType : category;
            if (type == null || type.trim().isEmpty()) {
                type = "Custom";
            }

            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.saveProductImage(image);
            }

            CustomProduct product = new CustomProduct(name, description, price, type, imageUrl);
            product.setStock(stock != null ? stock : 100);
            product.setChest(chest);
            product.setWaist(waist);
            product.setLength(length);
            product.setInseam(inseam);
            product.setSleeve(sleeve);

            CustomProduct savedProduct = customProductRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Custom product uploaded successfully", savedProduct));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error saving image: " + e.getMessage(), null));
        }
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<CustomProduct>> updateCustomProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Double chest,
            @RequestParam(required = false) Double waist,
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double inseam,
            @RequestParam(required = false) Double sleeve,
            @RequestParam(required = false) MultipartFile image) {

        try {
            Optional<CustomProduct> existingProduct = customProductRepository.findById(id);
            if (!existingProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Custom product not found", null));
            }

            CustomProduct product = existingProduct.get();
            product.setName(name);
            product.setPrice(price);

            String type = (productType != null && !productType.trim().isEmpty()) ? productType : category;
            if (type != null && !type.trim().isEmpty()) {
                product.setProductType(type);
            }
            if (description != null) {
                product.setDescription(description);
            }
            if (stock != null) {
                product.setStock(stock);
            }

            product.setChest(chest);
            product.setWaist(waist);
            product.setLength(length);
            product.setInseam(inseam);
            product.setSleeve(sleeve);

            if (image != null && !image.isEmpty()) {
                if (product.getImageUrl() != null) {
                    imageService.deleteProductImage(product.getImageUrl());
                }
                String imageUrl = imageService.saveProductImage(image);
                product.setImageUrl(imageUrl);
            }

            CustomProduct updatedProduct = customProductRepository.save(product);
            return ResponseEntity.ok(new ApiResponse<>(true, "Custom product updated successfully", updatedProduct));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error saving image: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomProduct(@PathVariable Long id) {
        Optional<CustomProduct> product = customProductRepository.findById(id);

        if (!product.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Custom product not found", null));
        }

        if (product.get().getImageUrl() != null) {
            imageService.deleteProductImage(product.get().getImageUrl());
        }

        customProductRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Custom product deleted successfully", null));
    }
}
