package com.fascinatingcloudservices.usa4foryou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("currency_rates")
public class CurrencyRate {
    @Id
    private String currencyRateId;
    private String currencyKey;
    private BigDecimal currencyRate;
    private String currencyName;
    private String createdAt;
    private Boolean isDeleted;
}
