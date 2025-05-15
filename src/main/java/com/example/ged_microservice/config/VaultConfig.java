package com.example.ged_microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;

import java.net.URI;
import java.net.URI;

@Configuration
public class VaultConfig {

    @Value("${vault.uri}")
    private String vaultUri;

    @Value("${vault.token}")
    private String vaultToken;

    @Bean
    public VaultTemplate vaultTemplate() {
        VaultEndpoint endpoint = VaultEndpoint.from(URI.create(vaultUri));
        ClientAuthentication auth = new TokenAuthentication(vaultToken);
        return new VaultTemplate(endpoint, auth);
    }

    @Bean
    public VaultKeyValueOperations kvOperations(VaultTemplate vaultTemplate) {
        return vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }
}
