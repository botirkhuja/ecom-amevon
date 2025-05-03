package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.validations.AllLevelsValidation;
import com.fascinatingcloudservices.usa4foryou.validations.IdOnlyValidation;
import com.fascinatingcloudservices.usa4foryou.validations.MediumLevelsValidation;
import com.fascinatingcloudservices.usa4foryou.validations.NameOnlyValidation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class IdNameDto {
  @NotNull(message = "Id is mandatory", groups = {AllLevelsValidation.class, MediumLevelsValidation.class, IdOnlyValidation.class})
  private String id;

  @NotNull(message = "Name is mandatory", groups = {AllLevelsValidation.class, NameOnlyValidation.class})
  private String name;
}
