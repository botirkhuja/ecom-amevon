package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NameDto {

  private String id;

  @NotNull(message = "Name is mandatory")
  @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
  private String name;
}
