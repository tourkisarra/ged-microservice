package com.example.ged_microservice.service;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ApplicationInitializerService {

    private static final Logger LOGGER = Logger.getLogger(ApplicationInitializerService.class.getName());

    @Autowired private RestHighLevelClient elasticsearchClient;
    @Autowired private VaultKeyValueOperations kvOperations;

    @PostConstruct
    public void initialize() {
        initElasticsearchIndex();
        initVaultAesKey();
    }

    private void initElasticsearchIndex() {
        try {
            String indexName = "ged_documents";
            if (!elasticsearchClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT)) {
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                String mapping = "{ \"mappings\": { \"properties\": {" +
                        "\"document_id\": { \"type\": \"keyword\" }," +
                        "\"title\": { \"type\": \"text\", \"fields\": { \"suggest\": { \"type\": \"completion\" } } }," +
                        "\"content\": { \"type\": \"text\" }," +
                        "\"file_type\": { \"type\": \"keyword\" }," +
                        "\"created_date\": { \"type\": \"date\" }," +
                        "\"storage_url\": { \"type\": \"keyword\" }," +
                        "\"status\": { \"type\": \"keyword\" }," +
                        "\"workspace\": { \"type\": \"keyword\" }" +
                        "} } }";
                request.source(mapping, XContentType.JSON);
                elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
                LOGGER.info("Index Elasticsearch 'ged_documents' créé avec succès.");
            } else {
                LOGGER.info("Index Elasticsearch déjà existant.");
            }
        } catch (IOException e) {
            LOGGER.severe("Erreur création index Elasticsearch : " + e.getMessage());
        }
    }

    private void initVaultAesKey() {
        try {
            var existing = kvOperations.get("ged/aes-key");
            if (existing == null || existing.getData() == null || !existing.getData().containsKey("key")) {
                byte[] keyBytes = new byte[32]; // AES 256 bits
                new SecureRandom().nextBytes(keyBytes);
                String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
                kvOperations.put("ged/aes-key", Map.of("key", encodedKey));
                LOGGER.info("Clé AES encodée et stockée dans Vault.");
            } else {
                LOGGER.info("Clé AES déjà présente dans Vault.");
            }
        } catch (Exception e) {
            LOGGER.severe("Erreur initialisation Vault : " + e.getMessage());
        }
    }
}
