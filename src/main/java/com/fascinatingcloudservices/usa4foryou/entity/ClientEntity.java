package com.fascinatingcloudservices.usa4foryou.entity;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("clients")
public class ClientEntity implements Persistable<String> {
  @Id
  @JsonProperty("clientId")
  private String client_id;
  private String name;

  @JsonIgnore
  private Timestamp createdAt;

  @JsonIgnore
  @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "isNew")
  @Override
  @Transient
  public boolean isNew() {
    return createdAt == null || client_id == null;
  }

  public String getId() {
    return client_id;
  }

  @JsonIgnore
  private Timestamp updatedAt;
  @JsonIgnore
  private Boolean isDeleted;
}