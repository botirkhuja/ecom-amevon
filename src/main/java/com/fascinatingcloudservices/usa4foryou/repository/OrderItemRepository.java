package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.entity.OrderItem;

import reactor.core.publisher.Flux;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, String> {
    // Custom query methods can be defined here if needed
    // For example, to find order items by order ID:
    Flux<OrderItem> findByOrderId(String orderId);
    // You can also define other custom queries as needed
    // For example, to find order items by product ID:
    // Flux<OrderItem> findByProductId(String productId);
}
