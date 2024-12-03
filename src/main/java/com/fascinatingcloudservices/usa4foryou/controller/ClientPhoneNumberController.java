package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.model.ClientPhoneNumber;
import com.fascinatingcloudservices.usa4foryou.service.ClientPhoneNumberService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ClientPhoneNumber>> getAllPhoneNumbers(@PathVariable String clientId) {
        List<ClientPhoneNumber> phoneNumbers = phoneNumberService.findAllByClientId(clientId);
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addPhoneNumber(@PathVariable String clientId, @RequestBody ClientPhoneNumber phoneNumber) {
        Optional<Client> clientOptional = clientService.findById(clientId);

        if (clientOptional.isEmpty()) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        Client client = clientOptional.get();
        phoneNumber.setClient(client);  // Set the Client entity, not clientId directly
        ClientPhoneNumber savedPhoneNumber = phoneNumberService.save(phoneNumber);

        return new ResponseEntity<>(savedPhoneNumber, HttpStatus.CREATED);
    }
}