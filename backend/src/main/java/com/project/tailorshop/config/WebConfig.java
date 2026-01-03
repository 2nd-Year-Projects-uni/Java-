package com.project.tailorshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig {

    @Value("${upload.dir:uploads/products}")
    private String uploadDir;

    /**
     * Configure CORS and static resource handling for uploaded images
     */
    @Bean
    public WebMvcConfigurer corsAndStaticConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Allow CORS requests from frontend (localhost:3000 or any origin for development)
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false);
            }

            /**
             * Serve uploaded images from /uploads/** endpoints
             * Maps virtual path /uploads/** to actual file system directory
             */
                @Override
                public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Serve images from uploads root. Pattern /uploads/** should map to /uploads/** on disk.
                // Example: request /uploads/products/grey.jpg -> file:<project>/uploads/products/grey.jpg
                String uploadsRoot = Paths.get("uploads").toAbsolutePath().toString();
                registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadsRoot + "/");

                // Also serve common static resources
                registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
                }
        };
    }
}
