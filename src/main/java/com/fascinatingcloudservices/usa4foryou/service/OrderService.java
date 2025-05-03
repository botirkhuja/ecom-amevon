package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.OrderEntity;
import com.fascinatingcloudservices.usa4foryou.entity.OrderItem;
import com.fascinatingcloudservices.usa4foryou.repository.OrderItemRepository;
import com.fascinatingcloudservices.usa4foryou.repository.OrderRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Flux<OrderItem> getAllOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public Mono<OrderEntity> createNewOrder(OrderEntity order, List<OrderItem> orderItems) {
        String orderId = RandomIdGenerator.generateRandomId(20);
        order.setOrderId(orderId);
        return orderRepository
                .save(order.toBuilder()
                        .orderId(orderId)
                        .isNew(true)
                        .build())
                .map(savedOrder -> orderItems
                        .stream()
                        .map(orderItem -> {
                            return orderItem
                                    .toBuilder()
                                    .orderId(orderId)
                                    .isNew(true)
                                    .orderItemId(RandomIdGenerator
                                            .generateRandomId(20))
                                    .build();
                        })
                        .toList())
                .flatMapMany(orderItemRepository::saveAll)
                .collectList()
                .map(result -> order);
    }

    public Mono<OrderEntity> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }
    
    // getOrderItemsById
    public Flux<OrderItem> getOrderItemsByOrderId(String orderItemId) {
        return orderItemRepository.findByOrderId(orderItemId);
    }
}
