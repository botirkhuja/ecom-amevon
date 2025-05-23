package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.CurrencyRateEntity;
import com.fascinatingcloudservices.usa4foryou.model.CurrencyRateDto;
import com.fascinatingcloudservices.usa4foryou.service.CurrencyRateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/currency-rates")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping
    public Flux<CurrencyRateDto> findAll() {
        return currencyRateService.findAll()
                .map(this::convertToDto);
    }

    private CurrencyRateDto convertToDto(CurrencyRateEntity currencyRateEntity) {
        return CurrencyRateDto.builder()
                .id(currencyRateEntity.getCurrencyRateId())
                .isoCode(currencyRateEntity.getIsoCode())
                .name(currencyRateEntity.getName())
                .rate(currencyRateEntity.getRate())
                .build();
    }
}
