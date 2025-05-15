package com.example.ged_microservice.service;
import com.example.ged_microservice.config.NuxeoWebClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
@Service
public class VersioningService {

    @Autowired
    private NuxeoWebClientConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Mono<String> createMinorVersion(String documentId) {
        if (documentId == null || documentId.isBlank()) {
            return Mono.error(new IllegalArgumentException("Document ID cannot be null or empty"));
        }
        Map<String, Object> body = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("increment", "Minor");
        body.put("params", params);
        body.put("input", "doc:" + documentId);

        return config.getWebClient()
                .post()
                .uri("/automation/Document.CreateVersion")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> listVersions(String documentId) {
        return config.getWebClient()
                .get()
                .uri("/id/" + documentId + "/@versions")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Resource> downloadVersion(String versionId) {
        return config.getWebClient()
                .get()
                .uri("/id/" + versionId + "/@blob/file:content")
                .retrieve()
                .bodyToMono(byte[].class)
                .map(ByteArrayResource::new);
    }
}
