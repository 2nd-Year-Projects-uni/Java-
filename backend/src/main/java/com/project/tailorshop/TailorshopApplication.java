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
    CommandLineRunner addSampleProducts(ProductRepository productRepository) {
        return args -> {

            productRepository.deleteAll();


            Product greyMen = new Product(
                    "Grey Cotton Coat",
                    "Stylish grey coat",
                    16000,
                    "MEN",
                    "/uploads/products/grey.jpg"
            );
            productRepository.save(greyMen);


            Product greyWomen = new Product(
                    "Grey Cotton Coat",
                    "Elegant grey coat",
                    16000,
                    "WOMEN",
                    "/uploads/products/greyf.jpg"
            );
            productRepository.save(greyWomen);
        };
    }

}
