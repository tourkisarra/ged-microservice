package com.example.ged_microservice.service;

import com.example.ged_microservice.config.NuxeoWebClientConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class NuxeoService {
    @Autowired
    private NuxeoWebClientConfig config;

    public String createDocument(String parentPath, String title) {
        String json = String.format("""
            {
              "entity-type": "document",
              "name": "%s",
              "type": "File",
              "properties": {
                "dc:title": "%s"
              }
            }
            """, title.replace(" ", "_"), title);

        return config.getWebClient()
                .post()
                .uri("/path/" + parentPath)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Erreur WebClient : " + e.getMessage()))
                .block();
    }

    public Mono<String> getDocument(String id) {
        return config.getWebClient()
                .get()
                .uri("/id/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }


    public String listChildren(String parentPath) {
        return config.getWebClient()
                .get()
                .uri("/path/" + parentPath + "/@children")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteDocument(String id) {
        return config.getWebClient()
                .delete()
                .uri("/id/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    private String extractIdFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.get("uid").asText(); // Récupère "uid"
        } catch (Exception e) {
            throw new RuntimeException("Erreur parsing JSON : " + json);
        }
    }
    public String searchDocuments(String keyword) {
        String query = String.format("SELECT * FROM Document WHERE ecm:fulltext = '%s'", keyword);
        return config.getWebClient()
                .post()
                .uri("/query")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"params\": {\"query\": \"" + query + "\"}}")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String uploadFile(MultipartFile file, String parentPath, String title) {
        try {
            // Étape 1 – Créer un document vide dans Nuxeo
            String json = String.format("""
            {
              "entity-type": "document",
              "name": "%s",
              "type": "File",
              "properties": {
                "dc:title": "%s"
              }
            }
        """, title.replace(" ", "_"), title);

            String createDocResponse = config.getWebClient()
                    .post()
                    .uri("/path/" + parentPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Étape 2 – Récupération de l'ID du document Nuxeo
            String documentId = extractIdFromJson(createDocResponse);

            // Étape 3 – Upload du blob (le fichier réel)
            String uploadResponse = config.getWebClient()
                    .put()
                    .uri("/id/" + documentId + "/@blob/file:content")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(file.getResource())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Étape 4 – Indexation dans Elasticsearch
           // IndexedDocument indexedDoc = new IndexedDocument();
            //indexedDoc.setId(documentId);
            //indexedDoc.setTitle(title);
            //indexedDoc.setDescription("Fichier uploadé via Nuxeo");
            //indexedDoc.setUploadDate(LocalDateTime.now().toString());

            //elasticsearchService.indexDocument(indexedDoc);

            return " Fichier uploadé avec succès et indexé : " + documentId;

        } catch (Exception e) {
            return " Erreur upload : " + e.getMessage();
        }
    }


}
