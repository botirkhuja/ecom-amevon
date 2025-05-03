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
@Table(name = "order_items")
public class OrderItem implements Persistable<String> {
  private String orderItemId;
  private String orderId;
  private String productId;
  private Integer quantity;
  private Double price;
  private String discountId;

  @Transient
  private boolean isNew;

  @Override
  public String getId() {
      return this.orderItemId;
  }

  @Override
  @Transient
  public boolean isNew() {
      return this.isNew;
  }

}
