package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddressDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ClientAddressRepository extends ReactiveCrudRepository<ClientAddressEntity, String> {
    Flux<ClientAddressEntity> findByClientId(String clientId);
}
