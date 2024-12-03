package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.ClientSocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientSocialAccountRepository extends JpaRepository<ClientSocialAccount, String> {
    List<ClientSocialAccount> findByClientId(String clientId);
}
