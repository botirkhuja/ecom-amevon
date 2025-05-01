package com.fascinatingcloudservices.usa4foryou.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderItemDto {
    @NotNull(message = "Product ID is mandatory")
    private String id;
    @Builder.Default
    private Integer quantity = 1;
    private Double price;
    private DiscountDto discount;  // Discount per product


  public BigDecimal getTotalPrice() {
    return BigDecimal.valueOf(this.price)
        .subtract(this.discount != null ? BigDecimal.valueOf(this.discount.getDiscountAmount()) : BigDecimal.ZERO)
        .multiply(BigDecimal.valueOf(this.quantity));
  }
}
