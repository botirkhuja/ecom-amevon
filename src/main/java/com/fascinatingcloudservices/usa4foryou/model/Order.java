package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Order {
    private String id;
    @NotNull(message = "Client is mandatory")
    @Valid
    private ClientDto client;
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    @Valid
    private DiscountDto discount;
    @Valid
    private CurrencyRateDto currencyRate;
    @Valid
    private ClientAddressDto shippingAddress;
    @Valid
    private ClientPhoneNumberEntity contactPhoneNumber;
    @NotNull(message = "Order items are mandatory")
    private List<OrderItemDto> orderItems;

    public BigDecimal getTotalPriceInCurrency() {
        if (currencyRate == null || currencyRate.getRate() == null) {
            return totalPrice;
        }
        if (totalPrice == null) {
            totalPrice = BigDecimal.ZERO;
            for (OrderItemDto orderItem : orderItems) {
                totalPrice = totalPrice.add(orderItem.getTotalPrice());
            }
        }
        return totalPrice.multiply(BigDecimal.valueOf(currencyRate.getRate()));
    }
}
