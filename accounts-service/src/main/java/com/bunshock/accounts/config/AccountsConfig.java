package com.bunshock.accounts.config;

import com.bunshock.accounts.dto.AccountsContactInfoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class AccountsConfig {

    @Bean
    @ConfigurationProperties(prefix = "accounts")
    @ConditionalOnMissingBean
    public AccountsContactInfoDTO accountsContactInfo() {
        return new AccountsContactInfoDTO(
            "Welcome to HipBank accounts microservice (config server down! Information unavailable)",
            Map.of("name", "unknown", "email", "unknown"),
            List.of("unknown")
        );
    }

}
