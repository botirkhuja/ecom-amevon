package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, String> {
}
