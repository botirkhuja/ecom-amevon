package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.Brand;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface BrandRepo extends ReactiveCrudRepository<Brand, String> {
    Mono<Brand> findByName(String name);

    // Find products whose name contains the given string (case-insensitive)
    @Query("SELECT b FROM Brand b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Mono<Brand> findByNameContainingIgnoreCase(String name);
}
