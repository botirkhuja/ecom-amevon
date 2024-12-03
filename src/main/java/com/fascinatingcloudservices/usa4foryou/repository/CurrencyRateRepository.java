package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.CurrencyRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CurrencyRateRepository extends ReactiveCrudRepository<CurrencyRate, String> {
    Mono<CurrencyRate> findByCurrencyRateId(String CurrencyRateId);
}
