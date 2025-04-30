package com.fascinatingcloudservices.usa4foryou.repository.mapper;

import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.r2dbc.core.ColumnMapRowMapper;

import com.fascinatingcloudservices.usa4foryou.entity.ClientEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.ErrorOnServerException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

public class ClientMapper implements BiFunction<Row, RowMetadata, ClientEntity> {

  @Override
  public ClientEntity apply(Row row, RowMetadata metadata) {
    ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
    Map<String, Object> result = rowMapper.apply(row, metadata);

    ObjectMapper objectMapper = new ObjectMapper();
    
    ClientEntity clientEntity;
    try {
      objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      clientEntity = objectMapper.convertValue(result, ClientEntity.class);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw new ErrorOnServerException("Could not convert clients with addresses result to entites");
    }

    return clientEntity;
  }

}
