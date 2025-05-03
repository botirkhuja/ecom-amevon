package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.repository.ClientAddressRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientAddressService {

    private final ClientAddressRepository repo;

    public ClientAddressService(ClientAddressRepository repo) {
        this.repo = repo;
    }

    // public Flux<ClientAddressDto> findAll() {
    //     return repo.findAll();
    // }

    public Mono<ClientAddressEntity> findById(String id) {
        return repo.findById(id);
    }

    public Flux<ClientAddressEntity> findAllByClientId(String clientId) {
        return repo.findByClientId(clientId);
    }


    // public Mono<ClientAddressDto> save(ClientAddressDto clientAddress) {
    //     // return RetryUtils.retry(() -> {
    //         var address = clientAddress
    //                 .toBuilder()
    //                 .clientAddressId(
    //                         RandomIdGenerator.generateRandomId(4)
    //                 )
    //                 .isNew(true)
    //                 .createdAt(LocalDateTime.now())
    //                 .build();
    //         return repo.save(address);
    //     // });
    // }

    public Mono<ClientAddressEntity> save(ClientAddressEntity clientAddress) {
        return repo.save(clientAddress);
    }

    public Flux<ClientAddressEntity> saveAll(List<ClientAddressEntity> clientAddresses) {
        return repo.saveAll(clientAddresses);
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}