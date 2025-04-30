package com.fascinatingcloudservices.usa4foryou.service;

import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Service
public class StorageService {

  @Autowired
  private MinioClient minioClient;

  @Value("${minio.bucket.name}")
  private String bucketName;

  @Value("${minio.url}")
  private String minioUrl;

  public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
    try {
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!found) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
      }
      minioClient.putObject(
          PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
              inputStream, inputStream.available(), -1)
              .contentType(contentType)
              .build());
    } catch (Exception e) {
      throw new RuntimeException("Error occurred: " + e.getMessage());
    }
  }

  public String uploadImage(MultipartFile file) {
    String fileName = generateFileName(file);
    try (InputStream is = file.getInputStream()) {
      minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(bucketName)
              .object(fileName).stream(is, file.getSize(), -1)
              .contentType(file.getContentType())
              .build());
      return minioUrl + "/" + bucketName + "/" + fileName;
    } catch (Exception e) {
      throw new RuntimeException("Failed to store image file.", e);
    }
  }

  private String generateFileName(MultipartFile file) {
    return new Date().getTime() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
  }
}
