package com.fascinatingcloudservices.usa4foryou.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("stores")
public class StoreEntity implements Persistable<String> {
  @Id
  private String storeId;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean isDeleted;

  @Transient
  private boolean isNew;

  @Override
  public String getId() {
    return storeId;
  }

  @Override
  @Transient
  public boolean isNew() {
    return this.isNew || storeId == null;
  }
}
