package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.entity.ProductEntity;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "order_items")
public class OrderItem {

    @Id
    private String orderItemId;
    private Order order;
    private ProductEntity product;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;  // Discount per product
}
