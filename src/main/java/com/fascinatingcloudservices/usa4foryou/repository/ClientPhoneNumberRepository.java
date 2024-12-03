package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.ClientPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientPhoneNumberRepository extends JpaRepository<ClientPhoneNumber, String> {
    // Find all phone numbers by clientId
    List<ClientPhoneNumber> findByClientId(String clientId);
}
