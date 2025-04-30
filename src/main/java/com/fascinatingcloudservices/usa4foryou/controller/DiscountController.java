package com.fascinatingcloudservices.usa4foryou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fascinatingcloudservices.usa4foryou.entity.DiscountEntity;
import com.fascinatingcloudservices.usa4foryou.model.DiscountDto;
import com.fascinatingcloudservices.usa4foryou.service.DiscountService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

  @Autowired
  private DiscountService discountService;

  @GetMapping
  public Flux<DiscountDto> findAll() {
    return discountService.findAll()
        .map(this::convertToDto);
  }

  @PostMapping()
  public Mono<DiscountDto> createDiscount(@Valid @RequestBody DiscountDto discountDto) {
    return discountService.createDiscount(convertToEntity(discountDto)).map(this::convertToDto);
  }

  @GetMapping("/{id}")
  public Mono<DiscountDto> findById(@PathVariable String id) {
    return discountService.findById(id)
        .map(this::convertToDto);
  }

  private DiscountDto convertToDto(DiscountEntity discountEntity) {
    return DiscountDto.builder()
        .id(discountEntity.getId())
        .discountCurrencyId(discountEntity.getDiscountCurrencyId())
        .discountAmount(discountEntity.getDiscountAmount())
        .build();
  }

  private DiscountEntity convertToEntity(DiscountDto discountDto) {
    return DiscountEntity.builder()
        .discountId(discountDto.getId())
        .discountCurrencyId(discountDto.getDiscountCurrencyId())
        .discountAmount(discountDto.getDiscountAmount())
        .build();
  }

}
