package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;
import com.fascinatingcloudservices.usa4foryou.model.IdNameDto;
import com.fascinatingcloudservices.usa4foryou.model.ProductDto;
import com.fascinatingcloudservices.usa4foryou.service.BrandService;
import com.fascinatingcloudservices.usa4foryou.service.CategoryService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import com.fascinatingcloudservices.usa4foryou.service.SubCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Flux<ProductDto> getAllProducts() {
        return productService.getProducts()
                .flatMap(this::convertToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDto> createProduct(@RequestBody @Valid ProductDto product) {
        return productService.createProduct(convertToEntity(product)).flatMap(this::convertToDto);
    }

    @GetMapping("/{productId}")
    public Mono<ResponseEntity<ProductEntity>> getById(@PathVariable String productId) {
        return productService.findById(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{productId}")
    public Mono<ResponseEntity<ProductEntity>> updateById(@PathVariable String id,
            @Valid @RequestBody ProductEntity product) {
        return productService.findById(id)
                .flatMap(product1 -> {
                    product.setProductId(id);
                    return productService.createProduct(product);
                })
                .map(savedProduct -> new ResponseEntity<>(savedProduct, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String productId) {
        return productService.deleteProductById(productId)
                .map(deleted -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to search product by name and return productId
    @GetMapping("/search")
    public Mono<ResponseEntity<ProductEntity>> searchByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean caseSensitive) {
        return productService.findByName(name, caseSensitive)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ProductEntity convertToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }

    private Mono<ProductDto> convertToDto(ProductEntity product) {
        return Mono.just(modelMapper.map(product, ProductDto.class))
                .flatMap(productDto -> {
                    return addRelatedItems(productDto, product);
                });
    }

    private Mono<ProductDto> addRelatedItems(ProductDto productDto, ProductEntity product) {
        Boolean isBrandIdAvailable = product.getBrandId() != null;
        Boolean isCategoryIdAvailable = product.getCategoryId() != null;
        Boolean isSubCategoryIdAvailable = product.getSubCategoryId() != null;

        return Mono.just(productDto)
                .flatMap(productDto1 -> {
                    if (isBrandIdAvailable) {
                        return addBrandById(productDto1, product.getBrandId());
                    } else {
                        return Mono.just(productDto1);
                    }
                })
                .flatMap(productDto1 -> {
                    if (isCategoryIdAvailable) {
                        return addCategoryById(productDto1, product.getCategoryId());
                    } else {
                        return Mono.just(productDto1);
                    }
                })
                .flatMap(productDto1 -> {
                    if (isSubCategoryIdAvailable) {
                        return addSubCategoryById(productDto1, product.getSubCategoryId());
                    } else {
                        return Mono.just(productDto1);
                    }
                });
    }

    private Mono<ProductDto> addBrandById(ProductDto productDto, String brandId) {
        return brandService.findById(brandId)
                .map(brand -> {
                    return productDto.toBuilder()
                            .brand(modelMapper.map(brand, IdNameDto.class))
                            .build();
                });
    }

    private Mono<ProductDto> addCategoryById(ProductDto productDto, String categoryId) {
        return categoryService.findById(categoryId)
                .map(category -> {
                    return productDto.toBuilder()
                            .category(modelMapper.map(category, IdNameDto.class))
                            .build();
                });
    }

    private Mono<ProductDto> addSubCategoryById(ProductDto productDto, String subCategoryId) {
        return subCategoryService.findById(subCategoryId)
                .map(subCategory -> {
                    return productDto.toBuilder()
                            .subCategory(modelMapper.map(subCategory, IdNameDto.class))
                            .build();
                });
    }
}