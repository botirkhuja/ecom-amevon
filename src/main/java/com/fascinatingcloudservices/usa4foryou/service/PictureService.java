package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.Picture;
import com.fascinatingcloudservices.usa4foryou.repository.PictureRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;

import io.minio.MinioClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PictureService {

    @Autowired
    private PictureRepository repo;

    @Autowired
    private StorageService imageStorageService;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public Flux<Picture> findAll() {
        return repo.findAll();
    }

    public Mono<Picture> findById(String id) {
        return repo.findById(id);
    }

    public Flux<Picture> findAllByProductId(String productId) {
        return repo.findByProductId(productId);
    }

    public Mono<Picture> createPicture(Picture picture) {
        return repo.save(picture.toBuilder()
                .pictureId(RandomIdGenerator.generateRandomId(7))
                .isNew(true)
                .build());
    }

    public Mono<Picture> savePicture(MultipartFile imageFile, String productId, String storeId) {
        return Mono.just(imageStorageService.uploadImage(imageFile))
                .doOnError(e -> {
                    throw new RuntimeException("Failed to upload image.", e);
                }).flatMap(imgUrl -> {
                    return repo.save(Picture.builder()
                            .pictureId(RandomIdGenerator.generateRandomId(7))
                            .url(imgUrl)
                            .productId(productId)
                            .storeId(storeId)
                            .isNew(true)
                            .build());
                });
        // try {
        //     String imageUrl = imageStorageService.uploadImage(imageFile);
        //     return repo.save(Picture.builder()
        //             .pictureId(RandomIdGenerator.generateRandomId(7))
        //             .url(imageUrl)
        //             .isNew(true)
        //             .build());
        // } catch (Exception e) {
        //     throw new RuntimeException("Failed to save image.", e);
        // }
    }
}
