package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

        @NotNull(message = "Product ID is mandatory")
        private String id;
        @NotNull(message = "Quantity is mandatory")
        private Integer quantity;
        private DiscountDto discount;
}
