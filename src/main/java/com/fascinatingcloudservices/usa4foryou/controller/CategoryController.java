package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.CategoryEntity;
import com.fascinatingcloudservices.usa4foryou.entity.SubCategory;
import com.fascinatingcloudservices.usa4foryou.model.NameDto;
import com.fascinatingcloudservices.usa4foryou.service.CategoryService;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<NameDto> getAll() {
        return categoryService.findAll()
                .map(this::convertToDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<NameDto> getById(@PathVariable String id) {
        return categoryService.findById(id)
                .map(this::convertToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NameDto> create(@Valid @RequestBody NameDto categoryRequest) {
        return convertToEntity(categoryRequest)
                .flatMap(categoryService::createCategory)
                .map(this::convertToDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<NameDto> updateBrand(@PathVariable String id, @RequestBody NameDto category) {
        return convertToEntity(category)
                .flatMap(entity -> categoryService.updateCategoryById(id, entity))
                .map(this::convertToDto);
    }

    @GetMapping("/{id}/subcategories")
    @ResponseStatus(HttpStatus.OK)
    public Flux<NameDto> getSubcategories(@PathVariable String id) {
        return categoryService.findAllSubcategoriesByCategoryId(id)
                .map(this::convertSubCategoryToDto);
    }

    @PostMapping("/{id}/subcategories")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NameDto> createSubCategory(@PathVariable String id,
            @Valid @RequestBody NameDto subCategoryRequest) {
        return convertToSubCategoryEntity(subCategoryRequest)
                .flatMap(subCategory -> categoryService.createSubCategory(id, subCategory))
                .map(this::convertSubCategoryToDto);
    }

    @PutMapping("/{categoryId}/subcategories/{subCategoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<NameDto> updateSubCategory(@PathVariable String categoryId,
            @PathVariable String subCategoryId, @RequestBody NameDto subCategory) {
        return convertToSubCategoryEntity(subCategory)
                .flatMap(entity -> categoryService.updateSubCategoryById(subCategoryId, entity))
                .map(this::convertSubCategoryToDto);
    }

    private Mono<CategoryEntity> convertToEntity(NameDto category) {
        return Mono.just(modelMapper.map(category, CategoryEntity.class));
    }

    private NameDto convertToDto(CategoryEntity category) {
        return modelMapper.map(category, NameDto.class);
    }

    private NameDto convertSubCategoryToDto(SubCategory subCategory) {
        return modelMapper.map(subCategory, NameDto.class);
    }

    private Mono<SubCategory> convertToSubCategoryEntity(NameDto subCategory) {
        return Mono.just(modelMapper.map(subCategory, SubCategory.class));
    }
}
