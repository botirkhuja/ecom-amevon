package com.fascinatingcloudservices.usa4foryou.utils;

import org.springframework.dao.DataIntegrityViolationException;

public class ExceptionCheckers {
    // Helper method to check if the exception is due to a duplicate key
    public static boolean isDuplicateKeyException(DataIntegrityViolationException e) {
        Throwable cause = e.getRootCause();
        return cause != null && cause.getMessage().contains("Duplicate entry");
    }
}
