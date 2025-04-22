package com.example.ged_microservice.controller;

import com.example.ged_microservice.service.KeycloakAdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class AuthController {
    @Autowired
    private KeycloakAdminClientService keycloakService;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @PostMapping
    public String registerUser(@RequestBody Map<String, String> payload) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", payload.get("username"));
        user.put("enabled", true);
        user.put("emailVerified", true);
        user.put("firstName", payload.get("firstName"));
        user.put("lastName", payload.get("lastName"));
        user.put("email", payload.get("email"));
        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", payload.get("password"));
        credential.put("temporary", false);

        user.put("credentials", new Map[]{credential});

        return keycloakService.createUser(realm, adminUsername, adminPassword, user);
    }
}
