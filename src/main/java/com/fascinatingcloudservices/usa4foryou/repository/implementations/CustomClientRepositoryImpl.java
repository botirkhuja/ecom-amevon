package com.fascinatingcloudservices.usa4foryou.repository.implementations;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.r2dbc.core.DatabaseClient;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.entity.ClientEntity;
import com.fascinatingcloudservices.usa4foryou.repository.CustomClientRepository;
import com.fascinatingcloudservices.usa4foryou.repository.mapper.ClientMapper;

import reactor.core.publisher.Flux;

public class CustomClientRepositoryImpl implements CustomClientRepository {

  private DatabaseClient client;

  public CustomClientRepositoryImpl(DatabaseClient client) {
    this.client = client;
  }

  @Override
  public Flux<ClientEntity> findAllClientsWithAddresses() {
    String query = "SELECT * FROM clients_with_addresses_view";
    ClientMapper clientMapper = new ClientMapper();
    Flux<ClientEntity> clients = client.sql(query)
        .map(clientMapper::apply)
        .all();
    return clients;
  }

  // public Flux<ClientAddressEntity> findAllAddressesForClient(String clientId) {
  //   String query = "SELECT * FROM client_addresses_view WHERE client_id = :clientId";
  //   ClientMapper clientMapper = new ClientMapper();
  //   Flux<ClientAddressEntity> addresses = client.sql(query)
  //       .bind("clientId", clientId);
  //   return addresses;
  // }
}
