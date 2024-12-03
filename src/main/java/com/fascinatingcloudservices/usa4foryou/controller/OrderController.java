package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.model.Order;
import com.fascinatingcloudservices.usa4foryou.model.OrderItem;
import com.fascinatingcloudservices.usa4foryou.model.OrderRequest;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import com.fascinatingcloudservices.usa4foryou.service.OrderService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;

    public OrderController(OrderService orderService, ClientService clientService, ProductService productService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    // GET to retrieve all orders
    @GetMapping
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    // GET to retrieve an order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrder(
            @PathVariable String orderId
    ) {
        return orderService.getOrderById(orderId)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND));
    }

    // POST to create a new order with products, shipping info, etc.
    @PostMapping
    public Mono<ResponseEntity<Object>> createOrder(
            @Valid @RequestBody OrderRequest orderRequest
    ) {
        Optional<Client> client = clientService.findById(orderRequest.getClientId());

        if (client.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not found"));
        }
        List<OrderItem> orderItems = this.orderService
                .convertOrderItemsRequestIntoOrderItems(orderRequest.getOrderItems());
        // if one of the order items are not found, return bad request
        if (orderItems.contains(null)) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found"));
        }

        return orderService.createOrder(orderItems, client.get())
                .map(orderService::attachOrderToOrderItems)
                .map(order -> orderService.setCurrencyToOrder(order, orderRequest.getCurrencyRateId()))
                .map(orderService::updateOrderTotalPrice)
                .map(orderService::save)
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order));
    }

    // Other methods like getOrder, updateOrder, etc.
}