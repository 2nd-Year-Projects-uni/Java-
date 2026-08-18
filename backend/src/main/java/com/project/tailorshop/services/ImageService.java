package com.project.tailorshop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Validate and upload image file to Cloudinary
     * @param file MultipartFile from request
     * @return Public Cloudinary image URL
     */
    public String saveProductImage(MultipartFile file) throws IOException {
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

        String fileExtension = getFileExtension(originalFilename).toLowerCase();
        if (!isAllowedExtension(fileExtension)) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: jpg, jpeg, png, gif, webp");
        }

        String fileNameWithoutExt = sanitizeFilename(originalFilename);
        String publicId = "products/" + UUID.randomUUID().toString().substring(0, 8) + "_" + fileNameWithoutExt;

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", publicId,
                    "resource_type", "auto"
            ));

            String secureUrl = (String) uploadResult.get("secure_url");
            if (!StringUtils.hasText(secureUrl)) {
                secureUrl = (String) uploadResult.get("url");
            }
            if (StringUtils.hasText(secureUrl)) {
                return secureUrl;
            }
        } catch (Exception e) {
            log.warn("Cloudinary upload failed or unreachable, saving image to local storage: {}", e.getMessage());
        }

        // Local storage fallback
        java.nio.file.Path uploadPath = java.nio.file.Paths.get("uploads/products");
        if (!java.nio.file.Files.exists(uploadPath)) {
            java.nio.file.Files.createDirectories(uploadPath);
        }
        String localFileName = UUID.randomUUID().toString().substring(0, 8) + "_" + sanitizeFilename(originalFilename) + "." + fileExtension;
        java.nio.file.Path targetLocation = uploadPath.resolve(localFileName);
        java.nio.file.Files.copy(file.getInputStream(), targetLocation, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return "uploads/products/" + localFileName;
    }

    /**
     * Delete an image from Cloudinary by its public URL or public ID
     */
    public boolean deleteProductImage(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            return false;
        }

        String publicId = extractPublicId(imageUrl);
        if (!StringUtils.hasText(publicId)) {
            return false;
        }

        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (Exception e) {
            log.error("Error deleting image from Cloudinary: {}", e.getMessage());
            return false;
        }
    }

    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return (lastDot > 0) ? filename.substring(lastDot + 1) : "";
    }

    private boolean isAllowedExtension(String extension) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    private String sanitizeFilename(String originalFilename) {
        int lastDot = originalFilename.lastIndexOf('.');
        String nameWithoutExtension = (lastDot > 0) ? originalFilename.substring(0, lastDot) : originalFilename;
        return nameWithoutExtension.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private String extractPublicId(String imageUrl) {
        try {
            if (imageUrl.contains("/upload/")) {
                String afterUpload = imageUrl.substring(imageUrl.indexOf("/upload/") + 8);
                if (afterUpload.matches("^v\\d+/.*")) {
                    afterUpload = afterUpload.substring(afterUpload.indexOf('/') + 1);
                }
                int lastDot = afterUpload.lastIndexOf('.');
                return (lastDot > 0) ? afterUpload.substring(0, lastDot) : afterUpload;
            }
            return imageUrl;
        } catch (Exception e) {
            log.warn("Could not parse Cloudinary image URL: {}", imageUrl);
            return null;
        }
    }

    /*
    ===================================================================
    PREVIOUS AWS S3 INTEGRATION IMPLEMENTATION (Kept as proof of work)
    ===================================================================

    // AWS S3 dependencies & fields:
    // private final S3Client s3Client;
    // private final String bucketName;
    // private final String region;

    // Previous AWS S3 Constructor:
    // public ImageService(
    //         @Value("${aws.s3.bucket}") String bucketName,
    //         @Value("${aws.region}") String region,
    //         @Value("${aws.accessKey:}") String accessKey,
    //         @Value("${aws.secretKey:}") String secretKey) {
    //     this.bucketName = bucketName;
    //     this.region = region;
    //     AwsCredentialsProvider credentialsProvider = buildCredentialsProvider(accessKey, secretKey);
    //     this.s3Client = S3Client.builder()
    //             .region(Region.of(region))
    //             .credentialsProvider(credentialsProvider)
    //             .build();
    // }

    // Previous S3 Image Upload Method:
    // public String saveProductImageS3(MultipartFile file) throws IOException {
    //     String objectKey = "products/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    //     PutObjectRequest putObjectRequest = PutObjectRequest.builder()
    //             .bucket(bucketName)
    //             .key(objectKey)
    //             .contentType(file.getContentType())
    //             .contentLength(file.getSize())
    //             .build();
    //     s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
    //     return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + objectKey;
    // }

    // Previous S3 Image Delete Method:
    // public boolean deleteProductImageS3(String imageUrl) {
    //     DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
    //             .bucket(bucketName)
    //             .key(extractObjectKey(imageUrl))
    //             .build();
    //     s3Client.deleteObject(deleteRequest);
    //     return true;
    // }
    */
}

