package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;

import reactor.core.publisher.Flux;

public interface ClientPhoneNumberRepository extends ReactiveCrudRepository<ClientPhoneNumberEntity, String> {
    // Find all phone numbers by clientId
    Flux<ClientPhoneNumberEntity> findByClientId(String clientId);
}
