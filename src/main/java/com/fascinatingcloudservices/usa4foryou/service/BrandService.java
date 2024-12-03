package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.Brand;
import com.fascinatingcloudservices.usa4foryou.repository.BrandRepo;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BrandService {

    private final BrandRepo repo;

    BrandService(BrandRepo repo) {
        this.repo = repo;
    }

    public Flux<Brand> findAll() {
        return repo.findAll();
    }

    public Mono<Brand> findById(String id) {
        return repo.findById(id);
    }

    public Mono<Brand> save(Brand brand) {
        return RetryUtils.retry(() -> {
            brand.setBrandId(new Brand().getBrandId());
            return repo.save(brand);
        });
    }

    public Mono<Brand> findByBrandName(String brandName, boolean caseSensitive) {
        if (caseSensitive) {
            return repo.findByName(brandName);
        } else {
            return repo.findByNameContainingIgnoreCase(brandName);
        }
    }
}
