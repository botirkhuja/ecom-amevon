package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    @Id
    private String clientId;
    @NotNull(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
    // private List<ClientNote> notes;
    @Valid
    private List<ClientPhoneNumberDto> phoneNumbers;
    @Valid
    private List<ClientAddressDto> addresses;
    // private List<ClientSocialAccount> socialAccounts;
    // private Timestamp createdAt;
    // private Timestamp updatedAt;
    // private Boolean isDeleted;

    // public Product setAsNew() {
    //     this.newProduct = true;
    //     return this;
    // }
}