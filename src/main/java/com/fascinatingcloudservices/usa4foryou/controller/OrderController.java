package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.entity.OrderEntity;
import com.fascinatingcloudservices.usa4foryou.entity.OrderItem;
import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.ClientNotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.model.DiscountDto;
import com.fascinatingcloudservices.usa4foryou.model.Order;
import com.fascinatingcloudservices.usa4foryou.model.OrderItemDto;
import com.fascinatingcloudservices.usa4foryou.model.OrderItemRequest;
import com.fascinatingcloudservices.usa4foryou.model.OrderCalculateRequest;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import com.fascinatingcloudservices.usa4foryou.service.CurrencyRateService;
import com.fascinatingcloudservices.usa4foryou.service.DiscountService;
import com.fascinatingcloudservices.usa4foryou.service.OrderService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final ClientService clientService;
    private final CurrencyRateService currencyRateService;

    @Autowired
    private DiscountService discountService;

    public OrderController(
            OrderService orderService,
            ClientService clientService,
            ProductService productService,
            CurrencyRateService currencyRateService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.currencyRateService = currencyRateService;
        this.productService = productService;
    }

    // GET to retrieve all orders
    @GetMapping
    public Flux<Order> getOrders() {
        return orderService.getAllOrders().map(this::convertToDto);
    }

    // GET to retrieve an order by ID
    // @GetMapping("/{orderId}")
    // public Mono<ResponseEntity<Order>> getOrder(
    // @PathVariable String orderId
    // ) {
    // return orderService.getOrderById(orderId)
    // .map(ResponseEntity::ok)
    // .defaultIfEmpty(ResponseEntity.notFound().build());
    // }

    // POST to create a new order with products, shipping info, etc.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(
            @Valid @RequestBody Order orderRequest) {
        return Mono.just(null);
        // var clientId = orderRequest.getClientId();
        // // Find the client by ID
        // var clientMono = clientService.findById(clientId);
        // var orderItemsRequest = orderRequest.getOrderItems();
        // // // Find the products by IDs
        // Mono<List<OrderItem>> productsMono =
        // orderService.convertOrderItemsRequestIntoOrderItems(orderItemsRequest)
        // .collectList();

        // var currencyRateId = orderRequest.getCurrencyRateId();
        // var currencyRateMono = currencyRateService.findByCurrencyId(currencyRateId)

        // .onErrorMap(e -> {
        // if (e instanceof NotFoundException) {
        // return e;
        // }
        // return new UnsupportedOperationException("An error occurred while finding the
        // currency rate");
        // });

        // return Mono.zip(clientMono, productsMono, currencyRateMono)
        // .flatMap(tuple -> {
        // var client = tuple.getT1();
        // var orderItems = tuple.getT2();
        // var currencyRate = tuple.getT2();

        // return orderService.createOrder(orderItems, client)
        // // .map(orderService::attachOrderToOrderItems)
        // // .flatMap(order -> orderService.setCurrencyToOrder(order,
        // orderRequest.getCurrencyRateId()))
        // // .map(orderService::updateOrderTotalPrice)
        // .flatMap(orderService::save)
        // .onErrorMap(e -> new UnsupportedOperationException("An error occurred while
        // saving the order. " + e.getMessage()));
        // })
        // .map(savedOrder -> {
        // return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        // })
        // .onErrorMap(e -> {
        // if (e instanceof NotFoundException) {
        // return e;
        // }
        // return new UnsupportedOperationException(e.getMessage());
        // });
    }

    @PostMapping("/calculate")
    public Mono<Order> calculateCart(@Valid @RequestBody OrderCalculateRequest orderRequest) {

        var orderMono = Mono
                .zip(
                        this.convertOrderItemsRequestIntoOrderItems(orderRequest.getOrderItems()),
                        findDiscountById(orderRequest.getDiscount().getId()))
                .flatMap(tuple -> {
                    var orderItems = tuple.getT1();
                    var discount = tuple.getT2();

                    // Calculate the total price of the order items
                    var totalPrice = calculateProductsTotalPrice(orderItems);

                    // Apply discount if available
                    if (discount != null) {
                        totalPrice = totalPrice.subtract(
                                BigDecimal.valueOf(discount.getDiscountAmount()));
                    }

                    // Create the order object
                    Order order = Order.builder()
                            .orderItems(orderItems)
                            .totalPrice(totalPrice)
                            .discount(discount)
                            .currencyRate(orderRequest.getCurrencyRate())
                            .build();

                    return Mono.just(order);
                });
        return orderMono;
    }

    private Mono<OrderItemDto> findProductOfOrderItemRequestAndGetOrderItemDto(OrderItemDto orderItemRequest) {
        var productId = orderItemRequest.getId();
        return productService.findById(productId).map(product -> {
            return OrderItemDto.builder()
                    .id(product.getId())
                    .quantity(orderItemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();
        })
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found " + productId)))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new UnsupportedOperationException("An error occurred while finding the product");
                });
    }

    private Mono<OrderItemDto> findDiscountAndAttachToOrderItemDto(
            DiscountDto discountDto,
            OrderItemDto orderItemDto) {
        return Optional.ofNullable(discountDto)
                .map(discount -> Optional.ofNullable(discount.getId()).orElseGet(null))
                .map(discountId -> {
                    return findDiscountById(discountId)
                            .map(discountEntity -> {
                                return orderItemDto.toBuilder()
                                        .discount(DiscountDto.builder()
                                                .id(discountEntity.getId())
                                                .discountCurrencyId(discountEntity.getDiscountCurrencyId())
                                                .discountAmount(discountEntity.getDiscountAmount())
                                                .build())
                                        .build();
                            }).switchIfEmpty(Mono.just(orderItemDto));
                })
                .orElse(Mono.just(orderItemDto));
    }

    private Mono<List<OrderItemDto>> convertOrderItemsRequestIntoOrderItems(List<OrderItemDto> orderItemsRequest) {
        var result = Flux.fromIterable(orderItemsRequest)
                .flatMap(this::findProductAndGetOrderItemDto)
                .collectList()
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
        return result;

    }

    // Other methods like getOrder, updateOrder, etc.
    // private Mono<OrderItemDto> findProductAndGetOrderItemDto(OrderItemRequest
    // orderItemRequest) {
    // return Mono
    // .zip(findProductById(orderItemRequest.getId()),
    // findDiscountById(orderItemRequest.getDiscountId()))
    // .map(tuple -> {
    // var product = tuple.getT1();
    // var discount = tuple.getT2();

    // return OrderItemDto.builder()
    // .productId(product.getId())
    // .quantity(orderItemRequest.getQuantity())
    // .price(product.getPrice())
    // .discount(discount)
    // .build();
    // })
    // .switchIfEmpty(Mono.error(new NotFoundException("Product not found " +
    // orderItemRequest.getId())))
    // .onErrorMap(e -> {
    // if (e instanceof NotFoundException) {
    // return e;
    // }
    // return new UnsupportedOperationException("An error occurred while finding the
    // product");
    // });
    // }

    private Mono<OrderItemDto> findProductAndGetOrderItemDto(OrderItemDto orderItemRequest) {
        return this.findProductOfOrderItemRequestAndGetOrderItemDto(orderItemRequest)
                .flatMap(orderItemDto -> this.findDiscountAndAttachToOrderItemDto(orderItemRequest.getDiscount(),
                        orderItemDto));
    }

    private Order convertToDto(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                // .clientId(orderEntity.getClientId())
                .orderDate(orderEntity.getOrderDate())

                // .orderItems(orderEntity.getOrderItems())
                .build();
    }

    private BigDecimal calculateProductsTotalPrice(List<OrderItemDto> products) {
        return products.stream()
                .map(OrderItemDto::getTotalPrice)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private Mono<DiscountDto> findDiscountById(String discountId) {
        return Optional.ofNullable(discountId)
                .map(id -> discountService.findById(id)
                        .map(discountEntity -> DiscountDto.builder()
                                .id(discountEntity.getId())
                                .discountCurrencyId(discountEntity.getDiscountCurrencyId())
                                .discountAmount(discountEntity.getDiscountAmount())
                                .build()))
                .orElse(Mono.empty());
        // Uncomment the following lines if you want to handle the case when
        // discountId is null

        // discountService.findById(discountId)
        // .map(discountEntity -> DiscountDto.builder()
        // .id(discountEntity.getId())
        // .discountCurrencyId(discountEntity.getDiscountCurrencyId())
        // .discountAmount(discountEntity.getDiscountAmount())
        // .build())
        // .switchIfEmpty(Mono.error(new NotFoundException("Discount not found " +
        // discountId)));
    }
}