package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.OrderEntity;
import com.fascinatingcloudservices.usa4foryou.model.*;
import com.fascinatingcloudservices.usa4foryou.repository.OrderRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CurrencyRateService currencyRateService;

    public OrderService(
            OrderRepository orderRepository,
            ProductService productService,
            CurrencyRateService currencyRateService
    ) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.currencyRateService = currencyRateService;
    }

    public Flux<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    // public Mono<Order> getOrderById(String orderId) {
    //     return orderRepository.findById(orderId);
    // }

//     public Mono<Order> createOrder(List<OrderItem> orderItems, ClientDto client) {
//         return Mono.just(
//                 Order.builder()
// //                        .client(client)
//                         .orderDate(Timestamp.from(Instant.now()))
//                         .createdAt(Timestamp.from(Instant.now()))
// //                        .orderItems(orderItems)
//                         .build()
//         )
//                 .map(this::addOrderId);
//     }

    // @Transactional
//     public Mono<Order> save(Order order) {
//             var savedData = orderRepository.save(order);
//             return savedData;
// //        return RetryUtils.retry(() -> order.flatMap(orderRepository::save));
//     }

    // public Order attachOrderToOrderItems(Order order) {
    //     for (OrderItem item : order.getOrderItems()) {
    //         item.setOrder(order);
    //     }
    //     return order;
    // }

    // public Flux<OrderItem> convertOrderItemsRequestIntoOrderItems(List<OrderItemRequest> orderItemsRequest) {
    //     return Flux.fromIterable(orderItemsRequest)
    //             .flatMap(orderItemRequest -> productService.findById(orderItemRequest.getProductId())
    //                     .map(product -> OrderItem.builder()
    //                             .product(product)
    //                             .quantity(orderItemRequest.getQuantity())
    //                             .discount(orderItemRequest.getDiscount())
    //                             .price(product.getPrice())
    //                             .build()
    //                     )
    //             );

    // }

    // private Order addOrderId(Order order) {
    //     return order.toBuilder().orderId(RandomIdGenerator.generateRandomId(5)).build();
    // }

    // private Order addOrderDate(Order order) {
    //     return order.toBuilder()
    //             .orderDate(Timestamp.from(Instant.now()))
    //             .createdAt(Timestamp.from(Instant.now()))
    //             .build();
    // }

    // public Order updateOrderTotalPrice(Order order) {
    //     return calculateOrderTotalPrice(order);
    // }

    // private Order calculateOrderTotalPrice(Order order) {

    //     BigDecimal totalPriceUsd = BigDecimal.ZERO;
    //     BigDecimal totalDiscount = BigDecimal.ZERO;
    //     for (OrderItem item : order.getOrderItems()) {
    //         totalPriceUsd = totalPriceUsd.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
    //         if (item.getDiscount() != null) {
    //             totalDiscount = totalDiscount.add(item.getDiscount());
    //         }
    //     }
    //     order.setTotalPriceUsd(totalPriceUsd);
    //     if (order.getCurrencyRate() != null && !order.getCurrencyRate().getCurrencyKey().equals("USD")) {
    //         BigDecimal currencyExchangeRate = order.getCurrencyRate().getCurrencyRate();
    //         if (currencyExchangeRate == null) throw new NullPointerException();
    //         // Convert the total price to the order currency
    //         order.setTotalPrice(totalPriceUsd.multiply(currencyExchangeRate));
    //     } else {
    //         order.setTotalPrice(totalPriceUsd);
    //     }
    //     order.setTotalDiscount(totalDiscount);
    //     return order;
    // }

    // public Mono<Order> setCurrencyToOrder(Order order, String currencyId) {
    //     return this.currencyRateService.findByCurrencyId(currencyId)
    //             .map(currencyRate -> order.toBuilder()
    //                     .currencyRate(currencyRate)
    //                     .build()
    //             );
    // }

    // Other methods like updateOrder, deleteOrder, etc.
}
