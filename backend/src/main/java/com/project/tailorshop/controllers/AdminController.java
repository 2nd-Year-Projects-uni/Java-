package com.project.tailorshop.controllers;

import com.project.tailorshop.dto.ApiResponse;
import com.project.tailorshop.entities.AdminUser;
import com.project.tailorshop.repositories.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminUser>> login(@RequestBody Map<String, String> creds) {
        try {
            String email = creds.get("email");
            String password = creds.get("password");
            System.out.println("Processing ADMIN login for: " + email);

            Optional<AdminUser> adminOpt = adminUserRepository.findByEmail(email);

            if (adminOpt.isPresent()) {
                AdminUser admin = adminOpt.get();
                if (admin.getPassword().equals(password)) {
                    return ResponseEntity.ok(new ApiResponse<>(true, "Admin Login success", admin));
                }
            }

            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid admin credentials", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Server Error: " + e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AdminUser>> createAdmin(@RequestBody AdminUser newAdmin) {
        try {
            if (adminUserRepository.findByEmail(newAdmin.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Admin email already exists", null));
            }
            // In a real app, you should hash the password here!
            AdminUser savedAdmin = adminUserRepository.save(newAdmin);
            return ResponseEntity.ok(new ApiResponse<>(true, "Admin created successfully", savedAdmin));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "Error: " + e.getMessage(), null));
        }
    }
}
