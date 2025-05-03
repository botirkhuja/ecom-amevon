package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.ClientAddressEntity;
import com.fascinatingcloudservices.usa4foryou.entity.ClientEntity;
import com.fascinatingcloudservices.usa4foryou.exceptions.ClientNotFoundException;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.model.NoteDto;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import com.fascinatingcloudservices.usa4foryou.service.NoteService;
import com.fascinatingcloudservices.usa4foryou.utils.NoteMapper;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private ModelMapper modelMapper;

    // GET all clients
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClientEntity> getAllClients() {
        return clientService.findAll();
    }

    // GET a single client by ID
    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientEntity> getClientById(@PathVariable String clientId) {
        return clientService
                .findById(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId)));
    }

    // @GetMapping("/{clientId}/address")
    // @ResponseStatus(HttpStatus.OK)
    // public Flux<ClientAddressEntity> getClientAddressById(@PathVariable String
    // clientId) {
    // return clientService
    // .findAddressesById(clientId);
    // }x

    // POST a new client
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientDto> save(@Valid @RequestBody ClientDto client) {
        return clientService
                .createNewClient(client)
                .map(clientEntity -> client.toBuilder().id(clientEntity.getId()).build())
                .flatMap(clientDto -> {
                    return Optional
                            .ofNullable(client.getNotes())
                            .map(notes -> {
                                var notesMono = noteService
                                        .createNotes(notes
                                                .stream()
                                                .map(note -> {
                                                    return note
                                                            .toBuilder()
                                                            .clientId(clientDto.getId())
                                                            .build();
                                                })
                                                .toList())
                                        .map(NoteMapper::convertToDtoList)
                                        .map(noteDtos -> clientDto.toBuilder()
                                                .notes(noteDtos)
                                                .build());
                                return notesMono;
                            })
                            .orElse(Mono.just(clientDto));
                });
    }

    // PUT to update an existing client
    @PutMapping("/{clientId}")
    public Mono<ClientEntity> updateClient(
            @PathVariable String clientId,
            @Valid @RequestBody ClientDto client) {
        return clientService.updateClient(clientId, client);
    }

    // DELETE a client by ID
    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteClient(@PathVariable String clientId) {
        return clientService.deleteById(clientId);
    }
}