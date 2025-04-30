package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.BrandEntity;

import reactor.core.publisher.Mono;

public interface BrandRepository extends R2dbcRepository<BrandEntity, String> {
    Mono<BrandEntity> findByName(String name);

    // Find products whose name contains the given string (case-insensitive)
    @Query("SELECT b FROM Brand b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Mono<BrandEntity> findByNameContainingIgnoreCase(String name);
}
