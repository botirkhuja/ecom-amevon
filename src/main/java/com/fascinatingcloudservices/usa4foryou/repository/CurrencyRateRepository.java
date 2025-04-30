package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.entity.CurrencyRateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CurrencyRateRepository extends ReactiveCrudRepository<CurrencyRateEntity, String> {
    Mono<CurrencyRateEntity> findByCurrencyRateId(String CurrencyRateId);
}
