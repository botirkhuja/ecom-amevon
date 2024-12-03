package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.ClientPhoneNumber;
import com.fascinatingcloudservices.usa4foryou.repository.ClientPhoneNumberRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientPhoneNumberService {

    private final ClientPhoneNumberRepository repo;

    public ClientPhoneNumberService(ClientPhoneNumberRepository repo) {
        this.repo = repo;
    }

    public List<ClientPhoneNumber> findAll() {
        return repo.findAll();
    }

    public Optional<ClientPhoneNumber> findById(String id) {
        return repo.findById(id);
    }

    // Method to find all phone numbers by clientId
    public List<ClientPhoneNumber> findAllByClientId(String clientId) {
        return repo.findByClientId(clientId);
    }

    public ClientPhoneNumber save(ClientPhoneNumber client) {
        return RetryUtils.retry(() -> {
            client.setId(new ClientPhoneNumber().getId());
            return repo.save(client);
        });
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}