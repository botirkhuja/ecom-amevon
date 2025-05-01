package com.fascinatingcloudservices.usa4foryou.model;

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
public class CurrencyRateDto {
    @NotNull(message = "Currency rate ID is mandatory")
    private String currencyRateId;
    private String isoCode;
    private Double rate;
    private String name;
}
