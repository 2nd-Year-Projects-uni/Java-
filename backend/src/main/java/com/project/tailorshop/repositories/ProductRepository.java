package com.project.tailorshop.repositories;

import com.project.tailorshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Optional: fetch products by category
    List<Product> findByCategory(String category);
}
