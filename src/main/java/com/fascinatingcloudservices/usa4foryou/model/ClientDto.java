package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fascinatingcloudservices.usa4foryou.validations.AllLevelsValidation;
import com.fascinatingcloudservices.usa4foryou.validations.MediumLevelsValidation;
import com.fascinatingcloudservices.usa4foryou.validations.NameOnlyValidation;
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
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    @Id
    @NotNull(message = "Client ID is mandatory", groups = {AllLevelsValidation.class, MediumLevelsValidation.class})
    private String id;
    @NotNull(message = "Name is mandatory", groups = {AllLevelsValidation.class, MediumLevelsValidation.class, NameOnlyValidation.class})
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