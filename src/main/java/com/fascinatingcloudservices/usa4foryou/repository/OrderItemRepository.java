package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.OrderItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, String> {
}
