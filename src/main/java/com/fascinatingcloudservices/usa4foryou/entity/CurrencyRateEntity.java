package com.fascinatingcloudservices.usa4foryou.entity;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "currency_rates")
public class CurrencyRateEntity implements Persistable<String> {
    @Id
    private String currencyRateId;
    private String isoCode;
    private String name;
    private Double rate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isDeleted;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return this.currencyRateId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.isDeleted == null || !this.isDeleted;
    }

}
