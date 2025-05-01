package com.fascinatingcloudservices.usa4foryou.entity;

import java.math.BigDecimal;

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
public class OrderItem {
  private String orderItemId;
  private String orderId;
  private String productId;
  private Integer quantity;
  private Double price;
  private String discountId;

}
