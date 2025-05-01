package com.fascinatingcloudservices.usa4foryou.entity;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "orders")
public class OrderEntity extends EntitySharedProperties implements Persistable<String> {
  @Id
  private String orderId;
  private String clientId;
  private Timestamp orderDate;
  private Double totalPrice;
  private String orderDiscountId;
  private String currencyRateId;

  private String shippingAddressId;
  private Double shippingFee;
  private String contactPhoneNumberId;
  private String orderTypeId;
  private Double taxAmount;
  private String storeId;

  @Transient
  private boolean isNew;

  @Override
  public String getId() {
    return this.orderId;
  }

  @Override
  @Transient
  public boolean isNew() {
    return this.isNew;
  }
}
