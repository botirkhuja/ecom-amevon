package com.fascinatingcloudservices.usa4foryou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fascinatingcloudservices.usa4foryou.entity.InventoryEntity;
import com.fascinatingcloudservices.usa4foryou.repository.InventoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  public Flux<InventoryEntity> getAllInventory() {
    return inventoryRepository.findAll();
  }

  public Mono<InventoryEntity> getInventoryById(Long id) {
    return inventoryRepository.findById(id);
  }

  public Mono<InventoryEntity> saveInventory(InventoryEntity inventory) {
    return inventoryRepository.save(inventory);
  }

  public void deleteInventoryById(Long id) {
    inventoryRepository.deleteById(id);
  }
}
