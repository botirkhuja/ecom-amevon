package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.entity.ClientEntity;
import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.ClientNotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddressDto;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.model.ClientPhoneNumberDto;
import com.fascinatingcloudservices.usa4foryou.repository.ClientRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientAddressService clientAddressService;
    private final ClientPhoneNumberService clientPhoneNumberService;

    public ClientService(ClientRepository clientRepository, ClientAddressService clientAddressService,
            ClientPhoneNumberService clientPhoneNumberService) {
        this.clientPhoneNumberService = clientPhoneNumberService;
        this.clientAddressService = clientAddressService;
        this.clientRepository = clientRepository;
    }

    public Flux<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    public Flux<ClientEntity> findAllClients() {
        return clientRepository.findAllClientsWithAddresses();
    }

    public Mono<ClientEntity> findById(String clientId) {
        return clientRepository.findById(clientId);
    }

    // public Flux<ClientAddressEntity> findAddressesById(String clientId) {
    // return clientRepository.findAllAddressesForClient(clientId)
    // .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId)));
    // }

    @Transactional
    public Mono<ClientEntity> createNewClient(ClientDto client) {
        ClientEntity clientEntity = ClientEntity.builder()
                .name(client.getName())
                .clientId(RandomIdGenerator.generateRandomId(20))
                .build();

        return clientRepository
                .save(clientEntity)
                .map(clientEntity1 -> converClientAddressesDtoToEntity(getClientAddressesList(client),
                        clientEntity1.getClientId()))
                .flatMapMany(clientAddresses -> {
                    return clientAddressService.saveAll(clientAddresses);
                })
                .map(x -> convertClientPhoneNumbersDtoToEntity(getClientPhoneNumberssList(client),
                        clientEntity.getClientId()))
                .flatMap(clientPhoneNumbers -> {
                    return clientPhoneNumberService.saveAll(clientPhoneNumbers);
                })
                .then(Mono.just(clientEntity));
    }

    public Mono<ClientEntity> updateClient(String clientId, ClientDto client) {
        return clientRepository.findById(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId)))
                .map(clientEntity -> clientEntity.toBuilder().name(client.getName()).build())
                .flatMap(clientRepository::save);
    }

    public Mono<Void> deleteById(String clientId) {
        return clientRepository.deleteById(clientId);
    }

    private List<ClientAddressDto> getClientAddressesList(ClientDto clientDto) {
        return Optional.ofNullable(clientDto.getAddresses()).orElse(Collections.emptyList());
    }

    private List<ClientPhoneNumberDto> getClientPhoneNumberssList(ClientDto clientDto) {
        return Optional.ofNullable(clientDto.getPhoneNumbers()).orElse(Collections.emptyList());
    }

    private List<ClientAddressEntity> converClientAddressesDtoToEntity(List<ClientAddressDto> clientAddresses,
            String clientId) {
        return clientAddresses.stream().map(clientAddressDto -> {
            return ClientAddressEntity.builder()
                    .clientAddressId(RandomIdGenerator.generateRandomId(10))
                    .street(clientAddressDto.getStreet())
                    .city(clientAddressDto.getCity())
                    .state(clientAddressDto.getState())
                    .zipCode(clientAddressDto.getZipCode())
                    .clientId(clientId)
                    .country(clientAddressDto.getCountry())
                    .isNew(true)
                    .build();
        }).toList();
    }

    private List<ClientPhoneNumberEntity> convertClientPhoneNumbersDtoToEntity(
            List<ClientPhoneNumberDto> clientPhoneNumbers, String clientId) {
        return clientPhoneNumbers.stream().map(clientPhoneNumber -> {
            return ClientPhoneNumberEntity.builder()
                    .clientPhoneNumberId(RandomIdGenerator.generateRandomId(10))
                    .phoneNumber(clientPhoneNumber.getPhoneNumber())
                    .phoneCountryCode(clientPhoneNumber.getPhoneCountryCode())
                    .clientId(clientId)
                    .isNew(true)
                    .build();
        }).toList();
    }
}