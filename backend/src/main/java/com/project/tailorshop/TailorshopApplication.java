package com.project.tailorshop;

import com.project.tailorshop.entities.Product;
import com.project.tailorshop.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TailorshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TailorshopApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            // Save a sample product
            Product sample = new Product(
                    "Premium Cotton Blazer",
                    "Hand-tailored from finest Italian cotton",
                    299.99,
                    "MEN",
                    "/images/blazer.jpg"
            );
            productRepository.save(sample);
        };
    }
}
