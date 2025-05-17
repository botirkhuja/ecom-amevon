package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.repository.ProductRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    public Flux<ProductEntity> getProducts() {
        return repo.findByIsDeletedFalse();
    }

    public Mono<ProductEntity> createProduct(ProductEntity product) {
        ProductEntity productEntity = product.toBuilder()
                .productId(Optional
                        .ofNullable(product.getProductId())
                        .orElse(RandomIdGenerator
                                .generateRandomId(20)))
                .isNew(true)
                .build();
        return repo.save(productEntity);
    }

    public Mono<ProductEntity> findById(String productId) {
        return RetryUtils.retry(() -> repo.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found " + productId)))

        );
    }

    public Mono<ProductEntity> findByName(String productName, boolean caseSensitive) {
        if (caseSensitive) {
            return repo.findByName(productName);
        } else {
            return repo.findByNameContainingIgnoreCase(productName);
        }
    }

    public Mono<ProductEntity> deleteProductById(String productId) {
        return findById(productId)
                .map(product -> product.toBuilder()
                        .isDeleted(true)
                        .build())
                .flatMap(repo::save)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found " + productId)));
    }
}
