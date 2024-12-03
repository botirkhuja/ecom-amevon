package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemRequest {

        @NotNull(message = "Product ID is mandatory")
        private String productId;
        @NotNull(message = "Quantity is mandatory")
        private Integer quantity;
        private BigDecimal discount;
}
