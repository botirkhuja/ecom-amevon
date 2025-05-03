package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientPhoneNumberRepository extends ReactiveCrudRepository<ClientPhoneNumberEntity, String> {
    // Find all phone numbers by clientId
    Flux<ClientPhoneNumberEntity> findByClientId(String clientId);
    Mono<ClientPhoneNumberEntity> findByPhoneNumber(String phoneNumber);
}
