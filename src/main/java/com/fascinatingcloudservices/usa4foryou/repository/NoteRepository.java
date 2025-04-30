package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.fascinatingcloudservices.usa4foryou.entity.NoteEntity;

import reactor.core.publisher.Flux;

public interface NoteRepository extends ReactiveCrudRepository<NoteEntity, String> {
  Flux<NoteEntity> findByClientId(String clientId);
  Flux<NoteEntity> findByOrderId(String orderId);
}
