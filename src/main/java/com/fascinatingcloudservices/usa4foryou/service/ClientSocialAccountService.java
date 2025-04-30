package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.ClientSocialAccount;
import com.fascinatingcloudservices.usa4foryou.repository.ClientSocialAccountRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientSocialAccountService {

    private final ClientSocialAccountRepository repo;

    public ClientSocialAccountService(ClientSocialAccountRepository repo) {
        this.repo = repo;
    }

    public Flux<ClientSocialAccount> findAll() {
        return repo.findAll();
    }

    public Mono<ClientSocialAccount> findById(String id) {
        return repo.findById(id);
    }

    // public Flux<ClientSocialAccount> findAllByClientId(String clientId) {
    //     return repo.findByClientId(clientId);
    // }


    public Mono<ClientSocialAccount> save(ClientSocialAccount client) {
        return RetryUtils.retry(() -> {
            client.setId(new ClientSocialAccount().getId());
            return repo.save(client);
        });
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}