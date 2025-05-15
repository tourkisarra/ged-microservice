package com.example.ged_microservice.service;

import com.example.ged_microservice.config.NuxeoWebClientConfig;
import com.example.ged_microservice.dto.DocumentDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NuxeoService {

    private final NuxeoWebClientConfig config;

    /**
     * Créer un document vide dans Nuxeo sous un chemin donné.
     */
    public String createDocument(String parentPath, String title) {
        try {
            String name = title.replace(" ", "_");
            String json = "{" +
                    "\"entity-type\": \"document\"," +
                    "\"name\": \"" + name + "\"," +
                    "\"type\": \"File\"," +
                    "\"properties\": {\"dc:title\": \"" + title + "\"}" +
                    "}";

            return config.getWebClient()
                    .post()
                    .uri("/path/" + parentPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Erreur création document : " + e.getMessage());
        }
    }

    /**
     * Récupérer les détails d'un document par son ID.
     */
    public Mono<String> getDocument(String id) {
        return config.getWebClient()
                .get()
                .uri("/id/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Lister tous les enfants d'un dossier donné.
     */
    public String listChildren(String parentPath) {
        try {
            return config.getWebClient()
                    .get()
                    .uri("/path/" + parentPath + "/@children")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Erreur listing children : " + e.getMessage());
        }
    }

    /**
     * Supprimer un document par son ID.
     */
    public String deleteDocument(String id) {
        try {
            return config.getWebClient()
                    .delete()
                    .uri("/id/" + id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Erreur suppression document : " + e.getMessage());
        }
    }

    /**
     * Effectuer une recherche full-text dans Nuxeo.
     */
    public String searchDocuments(String keyword) {
        try {
            String query = String.format("SELECT * FROM Document WHERE ecm:fulltext = '%s'", keyword);
            String body = "{ \"params\": { \"query\": \"" + query + "\" } }";

            return config.getWebClient()
                    .post()
                    .uri("/query")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Erreur recherche documents : " + e.getMessage());
        }
    }

    /**
     * Uploader un fichier dans Nuxeo.
     */
    public String uploadFile(MultipartFile file, String parentPath, String title) {
        try {
            // 1. Créer un document vide
            String name = title.replace(" ", "_");
            String json = "{" +
                    "\"entity-type\": \"document\"," +
                    "\"name\": \"" + name + "\"," +
                    "\"type\": \"File\"," +
                    "\"properties\": {\"dc:title\": \"" + title + "\"}" +
                    "}";

            String createDocResponse = config.getWebClient()
                    .post()
                    .uri("/path/" + parentPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 2. Extraire l'ID du document créé
            String documentId = extractIdFromJson(createDocResponse);

            // 3. Uploader le contenu réel (blob)
            config.getWebClient()
                    .put()
                    .uri("/id/" + documentId + "/@blob/file:content")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("file", file.getResource()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return " Fichier uploadé avec succès : " + documentId;

        } catch (Exception e) {
            throw new RuntimeException("Erreur upload fichier : " + e.getMessage());
        }
    }

    /**
     * Extraire l'UID du document depuis la réponse JSON de création.
     */
    private String extractIdFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.get("uid").asText();
        } catch (Exception e) {
            throw new RuntimeException("Erreur parsing JSON : " + json);
        }
    }
}
