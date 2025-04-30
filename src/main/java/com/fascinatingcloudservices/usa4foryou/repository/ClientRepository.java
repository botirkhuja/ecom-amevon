package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.entity.ClientEntity;

import reactor.core.publisher.Flux;

public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, String>, CustomClientRepository {

  // @Query("SELECT * FROM client_addresses_view WHERE client_id = :clientId")
  // Flux<ClientAddressEntity> findAllAddressesForClient(String clientId);
}

