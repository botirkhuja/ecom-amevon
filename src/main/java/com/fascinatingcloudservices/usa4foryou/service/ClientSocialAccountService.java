package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.ClientSocialAccount;
import com.fascinatingcloudservices.usa4foryou.repository.ClientSocialAccountRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientSocialAccountService {

    private final ClientSocialAccountRepository repo;

    public ClientSocialAccountService(ClientSocialAccountRepository repo) {
        this.repo = repo;
    }

    public List<ClientSocialAccount> findAll() {
        return repo.findAll();
    }

    public Optional<ClientSocialAccount> findById(String id) {
        return repo.findById(id);
    }

    public List<ClientSocialAccount> findAllByClientId(String clientId) {
        return repo.findByClientId(clientId);
    }


    public ClientSocialAccount save(ClientSocialAccount client) {
        return RetryUtils.retry(() -> {
            client.setId(new ClientSocialAccount().getId());
            return repo.save(client);
        });
    }

    public void deleteById(String clientId) {
        repo.deleteById(clientId);
    }
}