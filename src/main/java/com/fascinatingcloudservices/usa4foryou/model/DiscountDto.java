package com.fascinatingcloudservices.usa4foryou.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DiscountDto {
  private String id;

  @NotNull(message = "Discount currency ID cannot be null")
  private String discountCurrencyId;
  @NotNull(message = "Discount amount cannot be null")
  @Min(value = 0, message = "Discount amount must be greater than or equal to 0")
  private Double discountAmount;
}
