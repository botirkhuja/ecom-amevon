package com.fascinatingcloudservices.usa4foryou.repository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.fascinatingcloudservices.usa4foryou.entity.StoreEntity;

import io.r2dbc.spi.ConnectionFactory;

public interface StoreRepository extends R2dbcRepository<StoreEntity, String> {

}
