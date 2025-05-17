package com.fascinatingcloudservices.usa4foryou.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fascinatingcloudservices.usa4foryou.entity.InventoryEntity;
import com.fascinatingcloudservices.usa4foryou.model.Inventory;
import com.fascinatingcloudservices.usa4foryou.service.InventoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController(value = "/api/inventories")
@RequestMapping("/api/inventories")
public class InventoryController {

  @Autowired
  private InventoryService inventoryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<Inventory> getAllInventories() {
    return inventoryService.getAllInventory().map(this::convertToDto);
  }

  @PostMapping()
  public Mono<Inventory> saveInventory(@RequestBody Inventory inventory) {
      return inventoryService.saveInventory(convertToEntity(inventory))
              .map(this::convertToDto);
  }
  

  private InventoryEntity convertToEntity(Inventory inventory) {
    return new ModelMapper().map(inventory, InventoryEntity.class);
  }

  private Inventory convertToDto(InventoryEntity inventoryEntity) {
    return new ModelMapper().map(inventoryEntity, Inventory.class);
  }

}
