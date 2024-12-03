package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.ClientNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientNoteRepository extends JpaRepository<ClientNote, String> {
    List<ClientNote> findByClientId(String clientId);
}
