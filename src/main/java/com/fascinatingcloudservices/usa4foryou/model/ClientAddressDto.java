package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClientAddressDto {
    @Id
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
