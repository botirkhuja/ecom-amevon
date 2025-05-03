package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fascinatingcloudservices.usa4foryou.repository.ClientPhoneNumberRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientPhoneNumberService {

    private final ClientPhoneNumberRepository repo;

    public ClientPhoneNumberService(ClientPhoneNumberRepository repo) {
        this.repo = repo;
    }

    public Flux<ClientPhoneNumberEntity> findAll() {
        return repo.findAll();
    }

    public Flux<ClientPhoneNumberEntity> findAllByClientId(String id) {
        return repo.findByClientId(id);
    }

    // Method to find all phone numbers by clientId
    // public Flux<ClientPhoneNumber> findAllByClientId(String clientId) {
    //     return repo.findByClientId(clientId);
    // }

    public Mono<ClientPhoneNumberEntity> save(ClientPhoneNumberEntity client) {
        client.setClientPhoneNumberId(RandomIdGenerator.generateRandomId(10));
        return repo.save(client).retry(3);
    }

    public Flux<ClientPhoneNumberEntity> saveAll(Iterable<ClientPhoneNumberEntity> clients) {
        return repo.saveAll(clients);
    }
    
    public Mono<ClientPhoneNumberEntity> findById(String clientId) {
        return RetryUtils.retry(() -> repo.findById(clientId));
    }
    public Mono<ClientPhoneNumberEntity> findByPhoneNumber(String phoneNumber) {
        return repo.findByPhoneNumber(phoneNumber);
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}