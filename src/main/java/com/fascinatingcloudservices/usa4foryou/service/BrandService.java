package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.BrandEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.NameDto;
import com.fascinatingcloudservices.usa4foryou.repository.BrandRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class BrandService {

    private final BrandRepository repo;

    BrandService(BrandRepository repo) {
        this.repo = repo;
    }

    public BrandEntity createBrandInstance(String name) {
        return BrandEntity.builder()
                .brandId(RandomIdGenerator.generateRandomId(10))
                .name(name)
                .isNew(true)
                .build();
    }

    public Flux<BrandEntity> findAll() {
        return repo.findAll();
    }

    public Mono<BrandEntity> findById(String id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("id not found")));
    }

    public Mono<NameDto> findBrandById(String id) {
        return findById(id)
                .map(brand -> NameDto.builder()
                        .id(brand.getBrandId())
                        .name(brand.getName())
                        .build());
    }

    public Mono<BrandEntity> createNewBrand(NameDto brand) {
        return repo.save(createBrandInstance(brand.getName()));
    }

    public Mono<NameDto> updateBrandById(String id, NameDto brand) {
        return findById(id)
                .map(existingBrand -> existingBrand.toBuilder().name(brand.getName()).build())
                .flatMap(repo::save)
                .thenReturn(brand);
    }

    public Mono<BrandEntity> findByBrandName(String brandName, boolean caseSensitive) {
        if (caseSensitive) {
            return repo.findByName(brandName);
        } else {
            return repo.findByNameContainingIgnoreCase(brandName);
        }
    }
}
