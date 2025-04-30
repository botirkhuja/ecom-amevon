package com.fascinatingcloudservices.usa4foryou.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
  private String noteId;
  private String clientId;
  private String orderId;

  @NotNull(message = "Note is mandatory")
  private String note;
}
