package com.project.tailorshop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    // Allowed image types
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    public ImageService(
            @Value("${aws.s3.bucket}") String bucketName,
            @Value("${aws.region}") String region,
            @Value("${aws.accessKey:}") String accessKey,
            @Value("${aws.secretKey:}") String secretKey) {

        this.bucketName = bucketName;
        this.region = region;

        AwsCredentialsProvider credentialsProvider = buildCredentialsProvider(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    /**
     * Validate and save an uploaded image file to S3
     * @param file MultipartFile from request
     * @return Public S3 URL
     * @throws IllegalArgumentException if file is invalid
     * @throws IOException if upload fails
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

        String objectKey = generateObjectKey(originalFilename, fileExtension);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return buildPublicUrl(objectKey);
    }

    /**
     * Delete an image from S3 by its URL
     * @param imageUrl Public S3 URL
     * @return true if deleted, false otherwise
     */
    public boolean deleteProductImage(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            return false;
        }

        String objectKey = extractObjectKey(imageUrl);
        if (!StringUtils.hasText(objectKey)) {
            return false;
        }

        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.deleteObject(deleteRequest);
            return true;
        } catch (S3Exception e) {
            log.error("Error deleting image from S3: {}", e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    private AwsCredentialsProvider buildCredentialsProvider(String accessKey, String secretKey) {
        if (StringUtils.hasText(accessKey) && StringUtils.hasText(secretKey)) {
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
        }
        // Fallback to default provider chain (env vars, profile, etc.)
        return DefaultCredentialsProvider.create();
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

    private String generateObjectKey(String originalFilename, String extension) {
        int lastDot = originalFilename.lastIndexOf('.');
        String nameWithoutExtension = (lastDot > 0) ? originalFilename.substring(0, lastDot) : originalFilename;
        String sanitized = nameWithoutExtension.replaceAll("[^a-zA-Z0-9._-]", "_");
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return "products/" + uuid + "_" + sanitized + "." + extension;
    }

    private String buildPublicUrl(String objectKey) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + objectKey;
    }

    private String extractObjectKey(String imageUrl) {
        try {
            URI uri = URI.create(imageUrl);
            String path = uri.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            return path;
        } catch (IllegalArgumentException e) {
            log.warn("Could not parse image URL for deletion: {}", imageUrl);
            return null;
        }
    }
}
