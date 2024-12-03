package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Client;
import com.fascinatingcloudservices.usa4foryou.model.ClientNote;
import com.fascinatingcloudservices.usa4foryou.service.ClientNoteService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients/{clientId}/notes")
public class ClientNoteController {

    private final ClientNoteService clientNoteService;
    private final ClientService clientService;

    public ClientNoteController(ClientNoteService clientNoteService, ClientService clientService) {
        this.clientNoteService = clientNoteService;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientNote>> getAllByClientId(@PathVariable String clientId) {
        List<ClientNote> notes = clientNoteService.findAllByClientId(clientId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@PathVariable String clientId, @RequestBody ClientNote note) {
        Optional<Client> clientOptional = clientService.findById(clientId);

        if (clientOptional.isEmpty()) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        Client client = clientOptional.get();
        note.setClient(client);  // Set the Client entity, not clientId directly
        ClientNote savedNote = clientNoteService.save(note);

        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
}