package com.project.tailorshop.repositories;

import com.project.tailorshop.entities.CustomProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomProductRepository extends JpaRepository<CustomProduct, Long> {
    List<CustomProduct> findByProductType(String productType);
}
