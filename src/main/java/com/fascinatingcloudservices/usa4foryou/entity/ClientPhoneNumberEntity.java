package com.fascinatingcloudservices.usa4foryou.entity;

import jakarta.validation.constraints.NotNull;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "client_phone_numbers")
public class ClientPhoneNumberEntity implements Persistable<String> {
    @Id
    private String clientPhoneNumberId;
    @NotNull(message = "Phone number is mandatory")
    private String clientId;
    private String phoneNumber;
    private String phoneCountryCode;
    private Boolean isPrimary;
    private Boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Override
    public String getId() {
      return clientPhoneNumberId;
    }

    @Transient
    private boolean isNew;

    @Override
    @Transient
    public boolean isNew() {
      return this.isNew || this.clientPhoneNumberId == null;
    }
}
