package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.ClientPhoneNumberEntity;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.service.ClientPhoneNumberService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients/{clientId}/phone-numbers")
public class ClientPhoneNumberController {

    private final ClientPhoneNumberService phoneNumberService;
    private final ClientService clientService;

    public ClientPhoneNumberController(ClientPhoneNumberService phoneNumberService, ClientService clientService) {
        this.phoneNumberService = phoneNumberService;
        this.clientService = clientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClientPhoneNumberEntity> getAllPhoneNumbersByClientId(@PathVariable String clientId) {
        return phoneNumberService.findAllByClientId(clientId);
    }

    // @PostMapping
    // public Mono<ResponseEntity<ClientPhoneNumber>> addPhoneNumber(@PathVariable String clientId, @RequestBody ClientPhoneNumber phoneNumber) {
    //     return clientService
    //             .findById(clientId)
    //             .flatMap(client -> {
    //                 phoneNumber.setClient(client);
    //                 return phoneNumberService.save(phoneNumber);
    //             })
    //             .map(ResponseEntity::ok)
    //             .defaultIfEmpty(ResponseEntity.notFound().build());
    // }
}