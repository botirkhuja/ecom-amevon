package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fascinatingcloudservices.usa4foryou.validations.AllLevelsValidation;
import com.fascinatingcloudservices.usa4foryou.validations.MediumLevelsValidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class Order {
    private String id;
    @Valid
    @NotNull(message = "Client is mandatory", groups = {AllLevelsValidation.class})
    private ClientDto client;
    @Valid
    @NotNull(message = "Order type is mandatory", groups = {AllLevelsValidation.class, MediumLevelsValidation.class})
    private IdNameDto orderType;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    @Valid
    private ClientAddressDto shippingAddress;
    @Valid
    private ClientPhoneNumberDto contactPhoneNumber;
    @NotNull(message = "Order items are mandatory")
    @Valid
    private List<OrderItemDto> orderItems;  // The list of order items
    @Valid
    private DiscountDto discount;
    // @NotNull(message = "Currency rate id is mandatory")
    @Valid
    private CurrencyRateDto currencyRate;

    private BigDecimal shippingFee;

    public BigDecimal getTotalPrice() {
        if (totalPrice == null) {
            totalPrice = calculateOrderItemsTotalPrice();

            DiscountDto discount = this.getDiscount();
            if (discount != null && discount.getDiscountAmount() != null) {
                BigDecimal discountAmount = BigDecimal.valueOf(discount.getDiscountAmount());
                totalPrice = totalPrice.subtract(discountAmount);
            }
            BigDecimal shippingFee = this.getShippingFee();
            if (shippingFee != null) {
                totalPrice = totalPrice.add(shippingFee);
            }
        }

        return totalPrice;
    }

    private BigDecimal calculateOrderItemsTotalPrice() {
        return this.getOrderItems().stream()
                .map(OrderItemDto::getTotalPrice)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    public BigDecimal getTotalPriceInCurrency() {
        CurrencyRateDto currencyRate = this.getCurrencyRate();
        if (currencyRate == null || currencyRate.getRate() == null) {
            return totalPrice;
        }
        if (totalPrice == null) {
            totalPrice = this.getTotalPrice();
        }
        return totalPrice.multiply(BigDecimal.valueOf(currencyRate.getRate()));
    }
}
