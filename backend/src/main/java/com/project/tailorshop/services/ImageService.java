package com.project.tailorshop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.dir:uploads/products}")
    private String uploadDir;

    // Allowed image types
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    /**
     * Validate and save an uploaded image file
     * @param file MultipartFile from request
     * @return Relative path to saved file (e.g., "/uploads/products/uuid-filename.jpg")
     * @throws IllegalArgumentException if file is invalid
     * @throws IOException if file cannot be saved
     */
    public String saveProductImage(MultipartFile file) throws IOException {
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 5MB");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Invalid file name");
        }

        // Check file extension
        String fileExtension = getFileExtension(originalFilename).toLowerCase();
        if (!isAllowedExtension(fileExtension)) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: jpg, jpeg, png, gif, webp");
        }

        // Create uploads directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename to avoid conflicts
        String uniqueFilename = generateUniqueFilename(originalFilename, fileExtension);
        Path filePath = uploadPath.resolve(uniqueFilename);

        // Save file to disk
        Files.write(filePath, file.getBytes());

        // Return relative path for database storage
        return "/uploads/products/" + uniqueFilename;
    }

    /**
     * Delete an image file by its relative path
     * @param relativePath Path like "/uploads/products/filename.jpg"
     * @return true if deleted, false otherwise
     */
    public boolean deleteProductImage(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return false;
        }

        try {
            // Extract filename from relative path
            String filename = Paths.get(relativePath).getFileName().toString();
            Path filePath = Paths.get(uploadDir).resolve(filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error deleting image: " + e.getMessage());
        }

        return false;
    }

    /**
     * Get file extension from filename
     */
    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return (lastDot > 0) ? filename.substring(lastDot + 1) : "";
    }

    /**
     * Check if file extension is allowed
     */
    private boolean isAllowedExtension(String extension) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate unique filename using UUID to avoid conflicts
     */
    private String generateUniqueFilename(String originalFilename, String extension) {
        String nameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        String sanitized = nameWithoutExtension.replaceAll("[^a-zA-Z0-9._-]", "_");
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return uuid + "_" + sanitized + "." + extension;
    }
}
