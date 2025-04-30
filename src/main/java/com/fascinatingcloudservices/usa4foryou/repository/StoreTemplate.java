package com.fascinatingcloudservices.usa4foryou.repository;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import io.r2dbc.spi.ConnectionFactory;

public class StoreTemplate extends R2dbcEntityTemplate {

  public StoreTemplate(ConnectionFactory connectionFactory) {
    super(connectionFactory);
    //TODO Auto-generated constructor stub
  }
  
}