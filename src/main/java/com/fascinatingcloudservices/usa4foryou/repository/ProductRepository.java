package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, String> {
    Mono<ProductEntity> findByName(String name);

    // Find products where deleted is false
    Flux<ProductEntity> findByIsDeletedFalse();

    // Find products whose name contains the given string (case-insensitive)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Mono<ProductEntity> findByNameContainingIgnoreCase(String name);
}
