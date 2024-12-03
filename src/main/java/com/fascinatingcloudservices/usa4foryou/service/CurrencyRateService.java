package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.CurrencyRate;
import com.fascinatingcloudservices.usa4foryou.repository.CurrencyRateRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;

    CurrencyRateService(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    public Flux<CurrencyRate> findAll() {
        return currencyRateRepository.findAll();
    }

    public Mono<CurrencyRate> findByCurrencyId(String currencyCode) {
        return currencyRateRepository.findByCurrencyRateId(currencyCode);
    }
}
