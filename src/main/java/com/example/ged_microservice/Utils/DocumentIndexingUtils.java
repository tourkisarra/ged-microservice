package com.example.ged_microservice.Utils;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Utilitaire pour l'indexation des documents dans Elasticsearch.
 */
@Component
public class DocumentIndexingUtils {

    private static final Logger LOGGER = Logger.getLogger(DocumentIndexingUtils.class.getName());

    /**
     * Indexe un document dans Elasticsearch.
     * @param client Client Elasticsearch
     * @param document Données du document à indexer
     * @param documentId ID unique du document
     * @return true si l'indexation réussit, false sinon
     * @throws ElasticsearchIndexingException en cas d'erreur d'indexation
     */

    /**
     * Exception personnalisée pour les erreurs d'indexation Elasticsearch.
     */
    public static class ElasticsearchIndexingException extends RuntimeException {
        public ElasticsearchIndexingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception personnalisée pour les erreurs liées à Nuxeo.
     */
    public static class NuxeoException extends RuntimeException {
        public NuxeoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
