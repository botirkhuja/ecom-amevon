package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.ClientNote;
import com.fascinatingcloudservices.usa4foryou.repository.ClientNoteRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientNoteService {

    private final ClientNoteRepository repo;

    public ClientNoteService(ClientNoteRepository repo) {
        this.repo = repo;
    }

    public List<ClientNote> findAll() {
        return repo.findAll();
    }

    public Optional<ClientNote> findById(String id) {
        return repo.findById(id);
    }

    public List<ClientNote> findAllByClientId(String clientId) {
        return repo.findByClientId(clientId);
    }


    public ClientNote save(ClientNote clientNote) {
        return RetryUtils.retry(() -> {
            clientNote.setId(new ClientNote().getId());
            return repo.save(clientNote);
        });
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}