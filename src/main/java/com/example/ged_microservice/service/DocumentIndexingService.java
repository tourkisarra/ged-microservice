package com.example.ged_microservice.service;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class DocumentIndexingService {

    private static final Logger LOGGER = Logger.getLogger(DocumentIndexingService.class.getName());

    private final RestHighLevelClient elasticsearchClient;

    @Autowired
    public DocumentIndexingService(RestHighLevelClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public void indexDocument(String documentId, Map<String, Object> doc) {
        try {
            LOGGER.info(" Tentative d'indexation du document ID : " + documentId);
            IndexRequest request = new IndexRequest("ged_documents")
                    .id(documentId)
                    .source(doc, XContentType.JSON);
            elasticsearchClient.index(request, RequestOptions.DEFAULT);
            LOGGER.info(" Document indexé avec succès : " + documentId);
        } catch (IOException e) {
            LOGGER.severe(" Échec de l'indexation du document : " + e.getMessage());
            throw new RuntimeException("Erreur d'indexation : " + e.getMessage(), e);
        }
    }
}
