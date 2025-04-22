package com.example.ged_microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
@Component
public class NuxeoWebClientConfig {

    private final WebClient webClient;

    public NuxeoWebClientConfig(
            @Value("${nuxeo.url}") String baseUrl,
            @Value("${nuxeo.username}") String username,
            @Value("${nuxeo.password}") String password) {

        String auth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        System.out.println("🔐 Nuxeo Basic Auth: Basic " + auth);  // Pour vérifier si c’est correct

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + auth)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
