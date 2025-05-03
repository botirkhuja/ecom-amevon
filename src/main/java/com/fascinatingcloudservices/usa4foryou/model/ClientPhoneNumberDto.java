package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClientPhoneNumberDto {
  private String clientPhoneNumberId;
  private String clientId;
  @NotNull(message = "Phone number is mandatory")
  private String phoneNumber;
  @NotNull(message = "Phone country code is mandatory")
  private String phoneCountryCode;
}
