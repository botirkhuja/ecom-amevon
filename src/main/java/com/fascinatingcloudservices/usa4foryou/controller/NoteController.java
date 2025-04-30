package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.entity.NoteEntity;
import com.fascinatingcloudservices.usa4foryou.model.ClientDto;
import com.fascinatingcloudservices.usa4foryou.model.NoteDto;
import com.fascinatingcloudservices.usa4foryou.service.NoteService;
import com.fascinatingcloudservices.usa4foryou.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final ClientService clientService;

    public NoteController(NoteService clientNoteService, ClientService clientService) {
        this.noteService = clientNoteService;
        this.clientService = clientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<NoteEntity> getAll() {
        return noteService.findAll();
    }

    @GetMapping("/client/{clientId}")
    public Flux<NoteEntity> getAllByClientId(@PathVariable String clientId) {
        return noteService.findAllByClientId(clientId);
    }

    @GetMapping("/order/{orderId}")
    public Flux<NoteEntity> getAllByOrderId(@PathVariable String orderId) {
        return noteService.findAllByOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NoteEntity> add(@RequestBody NoteDto note) {
        return noteService.createNote(note);
    }
}