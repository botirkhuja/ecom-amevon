package com.fascinatingcloudservices.usa4foryou.model;

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
public class PictureDto {
  private String id;
  private String productId;
  private String storeId;
  private String url;
}
