package com.fascinatingcloudservices.usa4foryou.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "inventories")
public class InventoryEntity extends EntitySharedProperties implements Persistable<String> {
  private String inventoryId;
  private String productId;
  private int quantity;

  @Override
  public String getId() {
    return this.inventoryId;
  }

  @Transient
  private boolean isNew;

  @Override
  @Transient
  public boolean isNew() {
    return this.isNew;
  }

}
