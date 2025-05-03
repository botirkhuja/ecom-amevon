package com.fascinatingcloudservices.usa4foryou.service;

import org.springframework.stereotype.Service;

import com.fascinatingcloudservices.usa4foryou.entity.StoreEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.repository.StoreRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class StoreService {

  private final StoreRepository repo;

  StoreService(StoreRepository repo) {
    this.repo = repo;
  }

  public Flux<StoreEntity> findAll() {
    return repo.findAll();
  }

  public Mono<StoreEntity> findById(String id) {
    return repo.findById(id)
        .switchIfEmpty(
            Mono.error(new NotFoundException("id not found")));
  }

  public Mono<StoreEntity> createNewStore(String storeName) {
    var storeEntity = StoreEntity.builder()
        .storeId(RandomIdGenerator.generateRandomId(10))
        .name(storeName)
        .isNew(true)
        .build();
    return repo.save(storeEntity);
  }

  public Mono<StoreEntity> updateStoreNameById(String storeName, String id) {
    return findById(id)
        .map(existingStore -> existingStore
            .toBuilder()
            .name(storeName)
            .updatedAt(LocalDateTime.now())
            .build())
        .flatMap(repo::save);
  }
}
