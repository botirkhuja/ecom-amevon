package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddress;
import com.fascinatingcloudservices.usa4foryou.model.ClientNote;
import com.fascinatingcloudservices.usa4foryou.repository.ClientAddressRepository;
import com.fascinatingcloudservices.usa4foryou.repository.ClientNoteRepository;
import com.fascinatingcloudservices.usa4foryou.utils.ExceptionCheckers;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientAddressService {

    private final ClientAddressRepository repo;

    public ClientAddressService(ClientAddressRepository repo) {
        this.repo = repo;
    }

    public List<ClientAddress> findAll() {
        return repo.findAll();
    }

    public Optional<ClientAddress> findById(String id) {
        return repo.findById(id);
    }

    public List<ClientAddress> findAllByClientId(String clientId) {
        return repo.findByClientId(clientId);
    }


    public ClientAddress save(ClientAddress client) {
        return RetryUtils.retry(() -> {
            client.setId(new ClientAddress().getId());
            return repo.save(client);
        });
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}