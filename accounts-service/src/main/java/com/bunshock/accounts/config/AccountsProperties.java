package com.bunshock.accounts.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(value = "accounts")
@RefreshScope
@Getter @Setter
public class AccountsProperties {

    private String message = "Welcome to HipBank accounts microservice (config server down! Information unavailable)";
    private Map<String, String> contactDetails = Map.of("name", "unknown", "email", "unknown");
    private List<String> onCallSupport = List.of("unknown");

}
