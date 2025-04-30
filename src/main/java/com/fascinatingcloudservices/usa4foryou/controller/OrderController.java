package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.exceptions.ClientNotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.model.Order;
import com.fascinatingcloudservices.usa4foryou.model.OrderItem;
import com.fascinatingcloudservices.usa4foryou.model.OrderRequest;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import com.fascinatingcloudservices.usa4foryou.service.CurrencyRateService;
import com.fascinatingcloudservices.usa4foryou.service.OrderService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final CurrencyRateService currencyRateService;

    public OrderController(
            OrderService orderService,
            ClientService clientService,
            ProductService productService,
            CurrencyRateService currencyRateService
    ) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.currencyRateService = currencyRateService;
    }

    // GET to retrieve all orders
    @GetMapping
    public Flux<Order> getOrders() {
        return orderService.getAllOrders();
    }

    // GET to retrieve an order by ID
    @GetMapping("/{orderId}")
    public Mono<ResponseEntity<Order>> getOrder(
            @PathVariable String orderId
    ) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // POST to create a new order with products, shipping info, etc.
//     @PostMapping
//     public Mono<?> createOrder(
//             @Valid @RequestBody OrderRequest orderRequest
//     ) {

//         var clientId = orderRequest.getClientId();
//         // Find the client by ID
//         var clientMono = clientService.findById(clientId);
//         var orderItemsRequest = orderRequest.getOrderItems();
// //        // Find the products by IDs
//         Mono<List<OrderItem>> productsMono = orderService.convertOrderItemsRequestIntoOrderItems(orderItemsRequest)
//                 .collectList();

//         var currencyRateId = orderRequest.getCurrencyRateId();
//         var currencyRateMono = currencyRateService.findByCurrencyId(currencyRateId)

//                 .onErrorMap(e -> {
//                     if (e instanceof NotFoundException) {
//                         return e;
//                     }
//                     return new UnsupportedOperationException("An error occurred while finding the currency rate");
//                 });

//         return Mono.zip(clientMono, productsMono, currencyRateMono)
//                 .flatMap(tuple -> {
//                     var client = tuple.getT1();
//                     var orderItems = tuple.getT2();
//                     var currencyRate = tuple.getT2();

//                     return orderService.createOrder(orderItems, client)
// //                            .map(orderService::attachOrderToOrderItems)
// //                            .flatMap(order -> orderService.setCurrencyToOrder(order, orderRequest.getCurrencyRateId()))
// //                            .map(orderService::updateOrderTotalPrice)
//                             .flatMap(orderService::save)
//                             .onErrorMap(e -> new UnsupportedOperationException("An error occurred while saving the order. " + e.getMessage()));
//                 })
//                 .map(savedOrder -> {
//                     return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
//                 })
//                 .onErrorMap(e -> {
//                     if (e instanceof NotFoundException) {
//                         return e;
//                     }
//                     return new UnsupportedOperationException(e.getMessage());
//                 });
//     }

    // Other methods like getOrder, updateOrder, etc.
}