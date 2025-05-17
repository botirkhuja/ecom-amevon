package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Inventory {
  private Long inventoryId;
  @NotNull(message = "Product ID is mandatory")
  private String productId;
  @NotNull(message = "Quantity is mandatory")
  private int quantity;
}
