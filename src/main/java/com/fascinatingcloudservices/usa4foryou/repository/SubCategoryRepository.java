package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.SubCategory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubCategoryRepository extends ReactiveCrudRepository<SubCategory, String> {
    Flux<SubCategory> findByCategoryId(String categoryId);

    Mono<SubCategory> findByName(String name);

    Mono<SubCategory> findByNameAndCategoryId(String name, String categoryId);
}
