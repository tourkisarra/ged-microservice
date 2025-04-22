package com.example.ged_microservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class KeycloakAdminClientService {
    private final WebClient webClient;
    private String accessToken;
    public KeycloakAdminClientService(@Value("${keycloak.server.url}") String serverUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }

    public String getAccessToken(String username, String password, String realm) {
        return webClient.post()
                .uri("/realms/" + realm + "/protocol/openid-connect/token")
                .bodyValue("client_id=admin-cli&grant_type=password&username=" + username + "&password=" + password)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .block();
    }

    public String createUser(String realm, String adminUser, String adminPass, Map<String, Object> userPayload) {
        String token = getAccessToken(adminUser, adminPass, realm);

        return WebClient.builder()
                .baseUrl("http://localhost:8180")
                .defaultHeader("Authorization", "Bearer " + token)
                .defaultHeader("Content-Type", "application/json")
                .build()
                .post()
                .uri("/admin/realms/" + realm + "/users")
                .bodyValue(userPayload)
                .retrieve()
                .toBodilessEntity()
                .map(response -> "Utilisateur créé avec succès")
                .onErrorResume(e -> Mono.just("Erreur lors de la création : " + e.getMessage()))
                .block();
    }
}
