package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.entity.NoteEntity;
import com.fascinatingcloudservices.usa4foryou.model.NoteDto;
import com.fascinatingcloudservices.usa4foryou.repository.NoteRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NoteService {

    @Autowired
    private ModelMapper modelMapper;
    private final NoteRepository repo;

    public NoteService(NoteRepository repo) {
        this.repo = repo;
    }

    public Flux<NoteEntity> findAll() {
        return repo.findAll();
    }

    public Mono<NoteEntity> findById(String id) {
        return repo.findById(id);
    }

    public Flux<NoteEntity> findAllByClientId(String clientId) {
        return repo.findByClientId(clientId);
    }

    public Flux<NoteEntity> findAllByOrderId(String id) {
        return repo.findByOrderId(id);
    }

    public Mono<NoteEntity> createNote(NoteDto note) {
        NoteEntity noteEntity = NoteEntity.builder()
                .clientId(note.getClientId())
                .orderId(note.getOrderId())
                .note(note.getNote())
                .noteId(RandomIdGenerator.generateRandomId(20))
                .isNew(true)
                .build();
        return repo.save(noteEntity);
    }

    public Mono<List<NoteEntity>> createNotes(List<NoteDto> notes) {
        List<NoteEntity> noteEntities = notes.stream()
                .map(note -> NoteEntity.builder()
                        .clientId(note.getClientId())
                        .orderId(note.getOrderId())
                        .note(note.getNote())
                        .noteId(RandomIdGenerator.generateRandomId(20))
                        .isNew(true)
                        .build())
                .toList();
        return repo.saveAll(noteEntities).collectList();
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}