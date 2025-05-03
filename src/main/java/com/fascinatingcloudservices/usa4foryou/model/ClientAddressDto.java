package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClientAddressDto {
    private String clientAddressId;
    private String clientId;

    @NotNull(message = "Street is mandatory")
    private String street;

    private String city;
    private String state;
    private String zipCode;

    @NotNull(message = "Country is mandatory")  
    private String country;
}
