package com.fascinatingcloudservices.usa4foryou.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientWithAddressEntity extends ClientEntity {

  private String client_address_id;
  private String street;
  private String city;
  private String state;
  private String zip_code;
  private String country;
  private String shipping_type_label;
}
