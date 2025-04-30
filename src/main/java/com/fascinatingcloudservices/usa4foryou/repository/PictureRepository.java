package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.Picture;

import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface PictureRepository extends ReactiveCrudRepository<Picture, String> {
    Flux<Picture> findByProductId(String productId);
}
