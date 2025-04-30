package com.fascinatingcloudservices.usa4foryou.entity;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
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
@Table(name = "discounts")
public class DiscountEntity implements Persistable<String> {
  @Id
  private String discountId;
  private String discountCurrencyId;
  private Double discountAmount;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Boolean isDeleted;

  @Transient
  private boolean isNew;

  @Override
  public String getId() {
    return this.discountId;
  }

  @Override
  @Transient
  public boolean isNew() {
    return this.isDeleted == null || !this.isDeleted;
  }
}
