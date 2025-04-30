package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddressDto;
import com.fascinatingcloudservices.usa4foryou.service.ClientAddressService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clients/{clientId}/addresses")
public class ClientAddressController {

    private final ClientAddressService clientAddressService;
    private final ClientService clientService;

    public ClientAddressController(ClientAddressService clientAddressService, ClientService clientService) {
        this.clientAddressService = clientAddressService;
        this.clientService = clientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClientAddressEntity> getAllByClientId(@PathVariable String clientId) {
        return clientAddressService.findAllByClientId(clientId);
    }

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public Mono<ClientAddressEntity> add(@PathVariable String clientId, @RequestBody ClientAddressDto address) {
    //     return clientService
    //             .findById(clientId)
    //             .flatMap(client -> {
    //                 var address1 = address.toBuilder().clientId(client.getId()).build();
    //                 return clientAddressService.save(address1);
    //             });
    // }
}