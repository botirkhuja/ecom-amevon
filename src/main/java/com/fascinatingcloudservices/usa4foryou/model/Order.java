package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("orders")
public class Order {

    @Id
//    @Column(name = "order_id", length = 36)
    private String orderId;

//    @JsonManagedReference
//    @ManyToOne
//    @JoinColumn(name = "client_id", nullable = false)
    private ClientDto client;

//    @Column(name = "order_date")
    private Timestamp orderDate;

//    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

//    @Column(name = "total_price_usd", nullable = false)
    private BigDecimal totalPriceUsd;

//    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

//    @Column(name = "total_discount_usd")
    private BigDecimal totalDiscountUsd;

//    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

//    @Column(name = "shipping_fee_usd")
    private BigDecimal shippingFeeUsd;

//    @JsonManagedReference
//    @ManyToOne
//    @JoinColumn(name = "currency_rate_id", nullable = false)
    private CurrencyRateDto currencyRate;

    private String status;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    public enum PaymentMethod {
        CASH, CARD, PAYPAL, OTHER
    }

//    @JsonManagedReference
//    @ManyToOne
//    @JoinColumn(name = "shipping_address_id", nullable = true)
    private ClientAddressDto shippingAddress;

//    @JsonManagedReference
//    @ManyToOne
//    @JoinColumn(name = "contact_phone_number_id", nullable = true)
    private ClientPhoneNumberEntity contactPhoneNumber;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

//    @Column(name = "is_deleted")
    private Boolean isDeleted;

//    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

//    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

//    @PrePersist
//    protected void onCreate() {
////        this.orderId = RandomIdGenerator.generateRandomId(5);
//        this.createdAt = Timestamp.from(Instant.now());
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = Timestamp.from(Instant.now());
//    }
}
