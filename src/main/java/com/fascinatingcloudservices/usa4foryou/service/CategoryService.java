package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.CategoryEntity;
import com.fascinatingcloudservices.usa4foryou.entity.SubCategory;
import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.repository.CategoryRepository;
import com.fascinatingcloudservices.usa4foryou.repository.SubCategoryRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repo;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    public Flux<CategoryEntity> findAll() {
        return repo.findAll();
    }

    public Mono<CategoryEntity> findById(String id) {
        return repo.findById(id)
                .switchIfEmpty(
                        Mono.error(new NotFoundException("Category is not found")));
    }

    public Mono<CategoryEntity> createCategory(CategoryEntity category) {
        return repo.save(category.toBuilder()
                .categoryId(RandomIdGenerator.generateRandomId(10))
                .isNew(true)
                .build());
    }

    public Mono<CategoryEntity> updateCategoryById(String id, CategoryEntity category) {
        return repo.findById(id)
                .map(foundCategory -> category.toBuilder()
                        .categoryId(foundCategory.getCategoryId())
                        .isNew(false)
                        .build())
                .flatMap(repo::save)
                .switchIfEmpty(
                        Mono.error(new NotFoundException("Category is not found")));
    }

    public Flux<SubCategory> findAllSubcategoriesByCategoryId(String categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }

    public Mono<SubCategory> createSubCategory(String categoryId, SubCategory subCategory) {
        return repo.findById(categoryId)
                .map(category -> {
                    return subCategory.toBuilder()
                            .subCategoryId(RandomIdGenerator.generateRandomId(10))
                            .categoryId(categoryId)
                            .isNew(true)
                            .build();
                })
                .flatMap(subCategoryRepository::save)
                .switchIfEmpty(
                        Mono.error(new NotFoundException("Category is not found")));
    }

    public Mono<SubCategory> updateSubCategoryById(String subCategoryId, SubCategory subCategory) {
        return subCategoryRepository.findById(subCategoryId)
                .map(foundSubCategory -> foundSubCategory.toBuilder().name(subCategory.getName()).build())
                .flatMap(subCategoryRepository::save)
                .switchIfEmpty(
                        Mono.error(new NotFoundException("Subcategory is not found")));
    }
}
