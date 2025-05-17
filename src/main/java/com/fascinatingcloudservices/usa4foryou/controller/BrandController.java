package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.BrandEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.BrandDto;
import com.fascinatingcloudservices.usa4foryou.model.NameDto;
import com.fascinatingcloudservices.usa4foryou.service.BrandService;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    private ModelMapper modelMapper;

    BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<NameDto> getAllBrands() {
        return brandService.findAll()
                .map(this::convertToDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<NameDto> getBrandById(@PathVariable String id) {
        return brandService.findBrandById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NameDto> createBrand(@Valid @RequestBody NameDto brandPostRequest) {
        return brandService.createNewBrand(brandPostRequest).map(this::convertToDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<NameDto> updateBrand(@PathVariable String id, @RequestBody NameDto brand) {
        return brandService.updateBrandById(id, brand);
    }

    @GetMapping("/search")
    public Mono<NameDto> searchByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean caseSensitive) {
        return brandService.findByBrandName(name, caseSensitive)
                .map(this::convertToDto)
                .switchIfEmpty(Mono.error(new NotFoundException("Brand not found ")));
    }

    private NameDto convertToDto(BrandEntity brand) {
        return modelMapper.map(brand, NameDto.class);
    }

}
