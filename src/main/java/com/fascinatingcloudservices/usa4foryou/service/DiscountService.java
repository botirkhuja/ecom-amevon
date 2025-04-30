package com.fascinatingcloudservices.usa4foryou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fascinatingcloudservices.usa4foryou.entity.DiscountEntity;
import com.fascinatingcloudservices.usa4foryou.repository.DiscountRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiscountService {

  @Autowired
  private DiscountRepository discountRepository;

  public Flux<DiscountEntity> findAll() {
    return discountRepository.findAll();
  }

  public Mono<DiscountEntity> findById(String id) {
    return discountRepository.findById(id);
  }

  public Mono<DiscountEntity> createDiscount(DiscountEntity discount) {
    return discountRepository.save(discount.toBuilder()
        .discountId(RandomIdGenerator.generateRandomId(6))
        .isNew(true).build());
  }

  public Mono<DiscountEntity> updateDiscount(DiscountEntity discount) {
    return discountRepository.save(discount);
  }

  public Mono<Void> deleteDiscount(String id) {
    return discountRepository.deleteById(id);
  }
}
