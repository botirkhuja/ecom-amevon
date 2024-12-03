package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // GET all clients
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    // GET a single client by ID
    @GetMapping("/{clientId}")
    public ResponseEntity<Object> getClientById(@PathVariable String clientId) {
        Optional<Client> client = clientService.findById(clientId);
        return client
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND));
    }

    // POST a new client
    @PostMapping
    public ResponseEntity<Client> save(@Valid @RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    // PUT to update an existing client
    @PutMapping("/{clientId}")
    public ResponseEntity<Object> updateClient(@PathVariable String clientId, @Valid @RequestBody Client client) {
        Optional<Client> existingClient = clientService.findById(clientId);
        if (existingClient.isPresent()) {
            client.setId(clientId);
            Client updatedClient = clientService.save(client);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE a client by ID
    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable String clientId) {
        Optional<Client> existingClient = clientService.findById(clientId);
        if (existingClient.isPresent()) {
            clientService.deleteById(clientId);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
    }
}