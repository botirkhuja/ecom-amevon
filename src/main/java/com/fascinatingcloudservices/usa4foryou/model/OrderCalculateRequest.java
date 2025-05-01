package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCalculateRequest {
    @NotNull(message = "Order items are mandatory")
    @Valid
    private List<OrderItemDto> orderItems;  // The list of order items

    @Valid
    private DiscountDto discount;

    // @NotNull(message = "Currency rate id is mandatory")
    @Valid
    private CurrencyRateDto currencyRate;

}
