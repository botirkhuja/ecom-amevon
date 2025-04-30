package com.fascinatingcloudservices.usa4foryou.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "client_addresses")
public class ClientAddressEntity implements Persistable<String> {
  @JsonIgnore
  @Id
  private String clientAddressId;
  private String clientId;
  private String street;
  private String city;
  private String state;
  private String zipCode;
  private String country;

  @JsonIgnore
  private Boolean isDeleted;

  @CreatedDate
  @JsonIgnore
  private LocalDateTime createdAt;

  @JsonIgnore
  private LocalDateTime updatedAt;

  @JsonIgnore
  @Transient
  private boolean isNew;

  @Override
  public String getId() {
    return clientAddressId;
  }

  @JsonIgnore
  @Override
  @Transient
  public boolean isNew() {
    return this.isNew || this.clientAddressId == null;
  }
}
