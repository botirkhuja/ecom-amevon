package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.exceptions.NotFoundException;
import com.fascinatingcloudservices.usa4foryou.entity.OrderEntity;
import com.fascinatingcloudservices.usa4foryou.entity.OrderItem;
import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.ClientNotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddressDto;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.model.ClientPhoneNumberDto;
import com.fascinatingcloudservices.usa4foryou.model.CurrencyRateDto;
import com.fascinatingcloudservices.usa4foryou.model.DiscountDto;
import com.fascinatingcloudservices.usa4foryou.model.Order;
import com.fascinatingcloudservices.usa4foryou.model.OrderItemDto;
import com.fascinatingcloudservices.usa4foryou.model.OrderItemRequest;
import com.fascinatingcloudservices.usa4foryou.service.ClientAddressService;
import com.fascinatingcloudservices.usa4foryou.service.ClientPhoneNumberService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import com.fascinatingcloudservices.usa4foryou.service.CurrencyRateService;
import com.fascinatingcloudservices.usa4foryou.service.DiscountService;
import com.fascinatingcloudservices.usa4foryou.service.OrderService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import com.fascinatingcloudservices.usa4foryou.validations.MediumLevelsValidation;
import com.fascinatingcloudservices.usa4foryou.validations.MinimalLevelsValidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Autowired
    private ClientAddressService clientAddressService;
    @Autowired
    private ClientPhoneNumberService clientPhoneNumberService;

    @Autowired
    private ModelMapper modelMapper;

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
        return orderService.getAllOrders().flatMap(this::convertToDto);
    }

    // GET to retrieve an order by ID
    @GetMapping("/{orderId}")
    public Mono<Order> getOrder(
            @PathVariable String orderId) {
        return orderService.getOrderById(orderId)
                .switchIfEmpty(Mono.error(new NotFoundException("Order is not found")))
                .flatMap(this::convertToDto)
                .flatMap(order -> {
                    return orderService.getOrderItemsByOrderId(orderId).flatMap(
                            orderItem -> {
                                var orderItemDto = OrderItemDto.builder()
                                        .id(orderItem.getProductId())
                                        .quantity(orderItem.getQuantity())
                                        .price(orderItem.getPrice())
                                        // .discount(orderItem.getDiscount())
                                        .build();
                                return Optional.ofNullable(orderItem.getDiscountId())
                                        .map(discountId -> findDiscountById(discountId)
                                                .map(discount -> orderItemDto
                                                        .toBuilder()
                                                        .discount(discount)
                                                        .build())
                                                .defaultIfEmpty(orderItemDto))
                                        .orElse(Mono.just(orderItemDto));
                                // return orderItemDto;
                            })
                            .collectList()
                            .map(orderItemsList -> {
                                order.setOrderItems(orderItemsList);
                                return order;
                            });
                });
    }

    // POST to create a new order with products, shipping info, etc.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(
            @Validated(MediumLevelsValidation.class) @NotNull @RequestBody Order orderRequest) {
        return updateOrderDataFromDatabase(orderRequest).flatMap(order -> {
            var orderItemsEntity = order.getOrderItems().stream().map(this::convertToEntity).toList();
            var orderEntity = convertToEntity(order);
            var result = orderService.createNewOrder(orderEntity, orderItemsEntity)
                    .map(savedOrder -> order.toBuilder().id(savedOrder.getOrderId()).build());
            return result;
        });
    }

    @PostMapping("/calculate")
    public Mono<Order> calculateCart(
            @Valid @Validated(MinimalLevelsValidation.class) @NotNull @RequestBody Order orderRequest) {
        return this.updateOrderDataFromDatabase(orderRequest);

    }

    private Mono<Order> updateOrderDataFromDatabase(Order order) {
        var orderMono2 = this.updateOrderItemDtoListDetails(order.getOrderItems())
                .map(orderItems -> {
                    order.setOrderItems(orderItems);
                    return order;
                })
                .map(orderToUpdate -> {
                    if (orderToUpdate.getOrderDate() == null) {
                        orderToUpdate.setOrderDate(LocalDateTime.now());
                    }
                    return orderToUpdate;
                })
                .flatMap(orderToUpdate -> {
                    return findDiscount(orderToUpdate.getDiscount())
                            .map(discount -> {
                                orderToUpdate.setDiscount(discount);
                                return orderToUpdate;
                            })
                            .defaultIfEmpty(orderToUpdate);
                })
                .flatMap(orderToUpdate -> {
                    return findCurrencyRate(orderToUpdate.getCurrencyRate())
                            .map(currencyRate -> {
                                orderToUpdate.setCurrencyRate(currencyRate);
                                return orderToUpdate;
                            })
                            .defaultIfEmpty(orderToUpdate);
                })
                .flatMap(orderToUpdate -> {
                    return findClient(orderToUpdate.getClient())
                            .map(data -> {
                                orderToUpdate.setClient(data);
                                return orderToUpdate;
                            })
                            .defaultIfEmpty(orderToUpdate);
                })
                .flatMap(orderToUpdate -> {
                    return findClientAddress(orderToUpdate.getShippingAddress())
                            .map(data -> orderToUpdate
                                    .toBuilder()
                                    .shippingAddress(data)
                                    .build())
                            .defaultIfEmpty(orderToUpdate);
                })
                .flatMap(orderToUpdate -> {
                    return findClientPhoneNumber(orderToUpdate.getContactPhoneNumber())
                            .map(data -> orderToUpdate
                                    .toBuilder()
                                    .contactPhoneNumber(data)
                                    .build())
                            .defaultIfEmpty(orderToUpdate);
                });
        return orderMono2;
    }

    private Mono<List<OrderItemDto>> updateOrderItemDtoListDetails(List<OrderItemDto> orderItemsRequest) {
        var result = Flux.fromIterable(orderItemsRequest)
                .flatMap(this::updateOrderItemDtoPriceAndDiscount)
                .collectList()
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
        return result;
    }

    private Mono<OrderItemDto> updateOrderItemDtoPriceAndDiscount(OrderItemDto orderItemRequest) {
        return this.findProductPriceAndAttachToOrderItemDto(orderItemRequest)
                .flatMap(orderItemDto -> {
                    return findDiscount(orderItemDto.getDiscount())
                            .map(discount -> {
                                orderItemDto.setDiscount(discount);
                                return orderItemDto;
                            })
                            .defaultIfEmpty(orderItemDto);
                });
    }

    private Mono<OrderItemDto> findProductPriceAndAttachToOrderItemDto(OrderItemDto orderItemDto) {
        var productId = orderItemDto.getId();
        return productService.findById(productId).map(product -> {
            return orderItemDto.toBuilder()
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

    private Mono<DiscountDto> findDiscount(DiscountDto discount) {
        return Optional.ofNullable(discount)
                .map(d -> Optional.ofNullable(d.getId()).orElseGet(null))
                .map(this::findDiscountById)
                .orElse(Mono.empty());
    }

    private Mono<DiscountDto> findDiscountById(String discountId) {
        return discountService.findById(discountId)
                .map(discountEntity -> DiscountDto.builder()
                        .id(discountEntity.getId())
                        .discountCurrencyId(discountEntity.getDiscountCurrencyId())
                        .discountAmount(discountEntity.getDiscountAmount())
                        .build());
    }

    private Mono<CurrencyRateDto> findCurrencyRate(CurrencyRateDto currencyRate) {
        return Optional.ofNullable(currencyRate)
                .map(c -> Optional.ofNullable(c.getId()).orElseGet(null))
                .map(this::findCurrencyRateById)
                .orElse(Mono.empty());
    }

    private Mono<CurrencyRateDto> findCurrencyRateById(String currencyRateId) {
        return currencyRateService.findByCurrencyId(currencyRateId)
                .map(currencyRateEntity -> modelMapper.map(currencyRateEntity, CurrencyRateDto.class));
    }

    private Mono<ClientDto> findClient(ClientDto client) {
        return Optional.ofNullable(client)
                .map(c -> Optional.ofNullable(c.getId()).orElseGet(null))
                .map(this::findClientById)
                .orElse(Mono.empty());
    }

    private Mono<ClientDto> findClientById(String clientId) {
        return clientService.findById(clientId)
                .map(clientEntity -> modelMapper.map(clientEntity, ClientDto.class));
    }

    private Mono<ClientAddressDto> findClientAddress(ClientAddressDto clientAddressDto) {
        return Optional.ofNullable((clientAddressDto))
                .map(address -> Optional.ofNullable(address.getClientAddressId()).orElseGet(null))
                .map(this::findClientAddressById)
                .orElse(Mono.empty());
    }

    private Mono<ClientAddressDto> findClientAddressById(String addressId) {
        return clientAddressService.findById(addressId)
                .map(address -> modelMapper.map(address, ClientAddressDto.class));
    }

    private Mono<ClientPhoneNumberDto> findClientPhoneNumber(ClientPhoneNumberDto clientPhoneNumberDto) {
        return Optional.ofNullable((clientPhoneNumberDto))
                .map(phoneNumber -> Optional.ofNullable(phoneNumber.getClientPhoneNumberId()).orElseGet(null))
                .map(this::findClientPhoneNumberById)
                .orElse(Mono.empty());
    }

    private Mono<ClientPhoneNumberDto> findClientPhoneNumberById(String phoneId) {
        return clientPhoneNumberService.findById(phoneId)
                .map(phoneNumberEntity -> modelMapper.map(phoneNumberEntity, ClientPhoneNumberDto.class));
    }

    private Mono<Order> convertToDto(OrderEntity orderEntity) {

        return Mono.just(
                Order.builder()
                        .id(orderEntity.getOrderId())
                        .orderDate(orderEntity.getOrderDate())
                        .totalPrice(BigDecimal.valueOf(orderEntity.getTotalPrice()))
                        .shippingFee(BigDecimal.valueOf(orderEntity.getShippingFee()))
                        .build())
                .flatMap(order -> Optional.ofNullable(orderEntity.getClientId())
                        .map(id -> findClientById(id)
                                .map(client -> order
                                        .toBuilder()
                                        .client(client)
                                        .build())
                                .defaultIfEmpty(order))
                        .orElse(Mono.just(order)))
                .flatMap(order -> Optional.ofNullable(orderEntity.getCurrencyRateId())
                        .map(id -> findCurrencyRateById(id)
                                .map(currencyRate -> order.toBuilder()
                                        .currencyRate(currencyRate)
                                        .build())
                                .defaultIfEmpty(order))
                        .orElse(Mono.just(order)))
                .flatMap(order -> Optional.ofNullable(orderEntity.getOrderDiscountId())
                        .map(id -> findDiscountById(id)
                                .map(discount -> order.toBuilder()
                                        .discount(discount)
                                        .build())
                                .defaultIfEmpty(order))
                        .orElse(Mono.just(order)))
                .flatMap(order -> Optional.ofNullable(orderEntity.getShippingAddressId())
                        .map(id -> findClientAddressById(id)
                                .map(clientAddress -> order
                                        .toBuilder()
                                        .shippingAddress(clientAddress)
                                        .build())
                                .defaultIfEmpty(order))
                        .orElse(Mono.just(order)))
                .flatMap(order -> Optional.ofNullable(orderEntity.getContactPhoneNumberId())
                        .map(id -> findClientPhoneNumberById(id)
                                .map(phoneNumber -> order
                                        .toBuilder()
                                        .contactPhoneNumber(phoneNumber)
                                        .build())
                                .defaultIfEmpty(order))
                        .orElse(Mono.just(order)));
    }

    private OrderEntity convertToEntity(Order order) {
        var mappedOrder = modelMapper.map(order, OrderEntity.class);
        mappedOrder.setOrderId(order.getId());
        return mappedOrder;
    }

    private OrderItem convertToEntity(OrderItemDto orderItemDto) {
        var mappedOrderItem = modelMapper.map(orderItemDto, OrderItem.class);
        return mappedOrderItem.toBuilder().productId(orderItemDto.getId()).orderId(null).orderItemId(null).build();
    }
}