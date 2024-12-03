package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Brand;
import com.fascinatingcloudservices.usa4foryou.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public Flux<ResponseEntity<Brand>> getAllBrands() {
        return brandService.findAll()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Brand>> getBrandById(@PathVariable String id) {
        return brandService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Mono<ResponseEntity<Brand>> createBrand(@Valid @RequestBody Brand brand) {
        return brandService.save(brand)
                .map(brand1 -> ResponseEntity.status(HttpStatus.CREATED).body(brand1))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Brand>> updateBrand(@PathVariable String id, @RequestBody Brand brand) {
        return brandService.findById(id)
                .flatMap(existingBrand -> {
                    brand.setBrandId(id);
                    return brandService.save(brand)
                            .map(ResponseEntity::ok);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<Brand>> searchByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean caseSensitive
    ) {
        return brandService.findByBrandName(name, caseSensitive)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
