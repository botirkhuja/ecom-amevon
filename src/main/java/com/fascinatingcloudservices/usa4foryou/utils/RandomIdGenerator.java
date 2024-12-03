package com.fascinatingcloudservices.usa4foryou.utils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class RandomIdGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String generateRandomId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String generateTimeBasedId() {
        long timestamp = new Date().getTime();
        StringBuilder idBuilder = new StringBuilder(Long.toString(timestamp, 36)); // Base36 encoding for the timestamp

        for (int i = 0; i < 10; i++) { // Append 10 random characters
            idBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return idBuilder.toString();
    }
}