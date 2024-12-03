package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.repository.ClientRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(String clientId) {
        return clientRepository.findById(clientId);
    }

    public Client save(Client client) {
        return RetryUtils.retry(() -> {
            client.setId(new Client().getId());
            return clientRepository.save(client);
        });
    }


    public void deleteById(String clientId) {
        clientRepository.deleteById(clientId);
    }
}