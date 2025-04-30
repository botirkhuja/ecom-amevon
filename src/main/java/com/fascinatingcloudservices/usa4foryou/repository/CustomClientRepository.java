package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.entity.ClientEntity;

import reactor.core.publisher.Flux;

public interface CustomClientRepository {
  Flux<ClientEntity> findAllClientsWithAddresses ();
}
