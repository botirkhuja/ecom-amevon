package com.fascinatingcloudservices.usa4foryou.model;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductDto {
  private String id;

  @NotNull(message = "Name is mandatory")
  @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
  private String name;

  @Size(max = 65535, message = "Description must be less than 65535 characters")
  private String description;

  @Valid
  @NotNull(message = "Category is mandatory")
  private IdDto category;

  @Valid
  @NotNull(message = "Brand is mandatory")
  private IdDto brand;

  @Valid
  private IdDto subCategory;

  @NotNull(message = "Price is mandatory")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  private Double price;
  

}
