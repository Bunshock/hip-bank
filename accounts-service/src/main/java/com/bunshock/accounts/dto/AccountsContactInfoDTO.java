package com.bunshock.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(value = "accounts")
public record AccountsContactInfoDTO(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

    public AccountsContactInfoDTO(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
        this.message = (message == null) ? "Welcome to HipBank accounts microservice (config server down! Information unavailable)" : message;
        this.contactDetails = (contactDetails == null) ? Map.of("name", "unknown", "email", "unknown") : contactDetails;
        this.onCallSupport = (onCallSupport == null) ? List.of("unknown") : onCallSupport;
    }

}
