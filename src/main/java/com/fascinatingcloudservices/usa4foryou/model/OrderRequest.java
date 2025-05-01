package com.fascinatingcloudservices.usa4foryou.model;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest extends OrderCalculateRequest {
  @NotNull(message = "Client is mandatory")
  private ClientDto client;
  private Timestamp orderDate;
}
