package com.project.tailorshop.controllers;

import com.project.tailorshop.dto.ApiResponse;
import com.project.tailorshop.entities.User;
import com.project.tailorshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow frontend to call
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
        try {
            System.out.println("Processing registration for email: " + user.getEmail());
            // Check if email exists
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Email already in use", null));
            }

            // Save user (Note: In production, password should be hashed!)
            User savedUser = userRepository.save(user);
            System.out.println("User registered successfully via API");

            return ResponseEntity.ok(new ApiResponse<>(true, "Registration successful", savedUser));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Registration Failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody Map<String, String> creds) {
        try {
            String email = creds.get("email");
            String password = creds.get("password");
            System.out.println("Processing login for email: " + email);

            Optional<User> userOpt = userRepository.findByEmail(email);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Simple string comparison for now as requested for simplicity
                if (user.getPassword().equals(password)) {
                    return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", user));
                }
            }

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid email or password", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Login Failed: " + e.getMessage(), null));
        }
    }
}
