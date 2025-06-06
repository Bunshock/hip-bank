package com.bunshock.cards.service.impl;

import com.bunshock.cards.service.IdentifierGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CardNumberGeneratorService implements IdentifierGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    // Generates a 10-digit card number in the format of 0000000000 (as string)
    // between 1_000_000_000 to 9_999_999_999
    @Override
    public String generateId() {
        long number = 1_000_000_000L + (long)(secureRandom.nextDouble() * 9_000_000_000L);
        return String.format("%010d", number);
    }

}
