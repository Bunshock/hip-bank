package com.bunshock.cards.service;

public interface IdentifierGenerator {

    /**
     * Generates an identifier.
     * Implementation may vary depending on the type of identifier generated.
     *
     * @return the generated identifier as a String
     */
    String generateId();

}
