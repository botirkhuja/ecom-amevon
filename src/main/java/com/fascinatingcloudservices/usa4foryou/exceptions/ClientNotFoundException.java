package com.fascinatingcloudservices.usa4foryou.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class ClientNotFoundException extends NotFoundException {
    public ClientNotFoundException(String message) {
        super("Client not found " + message);
    }
}
