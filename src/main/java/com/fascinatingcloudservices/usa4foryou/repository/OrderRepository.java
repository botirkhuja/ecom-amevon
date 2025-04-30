package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
}