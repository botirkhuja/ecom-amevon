package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.ClientSocialAccount;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ClientSocialAccountRepository extends ReactiveCrudRepository<ClientSocialAccount, String> {
    // Flux<ClientSocialAccount> findByClientId(String clientId);
}
