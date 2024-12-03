package com.fascinatingcloudservices.usa4foryou.utils;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.function.Supplier;

public class RetryUtils {
    private static final int MAX_RETRIES = 3;

    public static <T> T retry(Supplier<T> action) {
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                return action.get();
            } catch (DataIntegrityViolationException e) {
                if (ExceptionCheckers.isDuplicateKeyException(e)) {
                    attempts++;
                    // Log the retry attempt
                    System.out.println("Duplicate entry detected, retrying... Attempt: " + attempts);
                } else {
                    throw e;
                }
            }
        }
        throw new RuntimeException("Failed to complete action after " + MAX_RETRIES + " attempts due to duplicate keys.");
    }
}
