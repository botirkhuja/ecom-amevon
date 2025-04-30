package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.Picture;
import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;
import com.fascinatingcloudservices.usa4foryou.model.PictureDto;
import com.fascinatingcloudservices.usa4foryou.service.PictureService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;

import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    private final PictureService pictureService;
    private final ProductService productService;

    public PictureController(PictureService pictureService, ProductService productService) {
        this.pictureService = pictureService;
        this.productService = productService;
    }

    @GetMapping
    public Flux<Picture> getAllByClientId() {
        return pictureService
                .findAll();
    }

    // @GetMapping
    // public Flux<ResponseEntity<Picture>> getAllByClientId(@PathVariable String
    // productId) {
    // return pictureService
    // .findAllByProductId(productId)
    // .map(ResponseEntity::ok);
    // }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Picture>> getById(@PathVariable String id) {
        return pictureService
                .findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveUrl")
    public Mono<Picture> postMethodName(@RequestBody PictureDto pictureDto) {
        return pictureService.createPicture(convertToEntity(pictureDto));
    }
    

    // consumes image/jpeg
    @PostMapping("/upload")
    public Mono<Picture> uploadNewImage(
            // @PathVariable String productId,
            @RequestPart(name = "productId", required = false) String productId,
            @RequestPart(name = "storeId", required = false) String storeId,
            @RequestPart(name = "image") MultipartFile image) {
        return pictureService.savePicture(image, productId, storeId);
    }

    private Picture convertToEntity(PictureDto pictureDto) {
        return Picture.builder()
                .pictureId(pictureDto.getId())
                .productId(pictureDto.getProductId())
                .storeId(pictureDto.getStoreId())
                .url(pictureDto.getUrl())
                .build();
    }
    private PictureDto convertToDto(Picture picture) {
        return PictureDto.builder()
                .id(picture.getPictureId())
                .productId(picture.getProductId())
                .storeId(picture.getStoreId())
                .url(picture.getUrl())
                .build();
    }
}