package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.CurrencyRateEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.CurrencyRateDto;
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

    public Flux<CurrencyRateEntity> findAll() {
        return currencyRateRepository.findAll();
    }

    public Mono<CurrencyRateEntity> findByCurrencyId(String currencyCode) {
        return currencyRateRepository.findByCurrencyRateId(currencyCode)
                .switchIfEmpty(Mono.error(new NotFoundException("Currency rate not found " + currencyCode)));
    }
}
