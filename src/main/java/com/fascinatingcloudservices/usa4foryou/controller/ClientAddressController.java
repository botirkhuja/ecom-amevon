package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddress;
import com.fascinatingcloudservices.usa4foryou.service.ClientAddressService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<ClientAddress>> getAllByClientId(@PathVariable String clientId) {
        List<ClientAddress> notes = clientAddressService.findAllByClientId(clientId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@PathVariable String clientId, @RequestBody ClientAddress address) {
        Optional<Client> clientOptional = clientService.findById(clientId);

        if (clientOptional.isEmpty()) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        Client client = clientOptional.get();
        address.setClient(client);  // Set the Client entity, not clientId directly
        ClientAddress savedAddress = clientAddressService.save(address);

        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }
}