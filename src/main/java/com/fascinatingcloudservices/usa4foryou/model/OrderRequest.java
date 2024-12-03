package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "Order items are mandatory")
    private List<OrderItemRequest> orderItems;  // The list of order items
    @NotNull(message = "Client ID is mandatory")
    private String clientId;

    private BigDecimal cartDiscount;
    private BigDecimal cartDiscountUsd;

    @NotNull(message = "Currency rate id is mandatory")
    private String currencyRateId;

    private Timestamp orderDate;
}
