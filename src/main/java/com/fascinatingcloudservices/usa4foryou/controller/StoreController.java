package com.fascinatingcloudservices.usa4foryou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fascinatingcloudservices.usa4foryou.entity.StoreEntity;
import com.fascinatingcloudservices.usa4foryou.model.NameDto;
import com.fascinatingcloudservices.usa4foryou.service.StoreService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

  @Autowired
  private StoreService storeService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<StoreEntity> getAllStores() {
    return storeService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<StoreEntity> getMethodName(@PathVariable String id) {
    return storeService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<StoreEntity> createStore(@Valid @RequestBody NameDto payload) {
    return storeService.createNewStore(payload.getName());
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<StoreEntity> updateStore(@PathVariable String id, @RequestBody NameDto payload) {
    return storeService.updateStoreNameById(payload.getName(), id);
  }

}
