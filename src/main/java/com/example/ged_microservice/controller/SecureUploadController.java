package com.example.ged_microservice.controller;

import com.example.ged_microservice.service.SecureUploadService;
import com.example.ged_microservice.Utils.DocumentIndexingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * Contrôleur REST réactif pour gérer l'upload sécurisé de documents.
 * Utilise Spring WebFlux pour une API asynchrone.
 */
@RestController
@RequestMapping("/api/documents")
public class SecureUploadController {

    @Autowired
    private SecureUploadService secureUploadService;

    /**
     * Endpoint pour uploader un document de manière sécurisée.
     *
     * @param file        Fichier à uploader (PDF, Word, Excel, JPEG, PNG)
     * @param parentPath  Chemin parent dans Nuxeo (ex. : /default-domain/workspaces)
     * @param title       Titre du document
     * @param workspace   Workspace pour l'indexation (ex. RH, Marketing, etc.)
     * @return Mono contenant une réponse HTTP avec message
     */
    @PostMapping("/uploadsecure")
    public Mono<ResponseEntity<String>> uploadSecure(
            @RequestParam("file") MultipartFile file,
            @RequestParam("parentPath") String parentPath,
            @RequestParam("title") String title,
            @RequestParam("workspace") String workspace
    ) {
        return secureUploadService.uploadSecureDocument(file, parentPath, title, workspace)
                .map(ResponseEntity::ok)
                .onErrorResume(DocumentIndexingUtils.NuxeoException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(" Erreur Nuxeo : " + e.getMessage())))
                .onErrorResume(DocumentIndexingUtils.ElasticsearchIndexingException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(" Erreur d'indexation Elasticsearch : " + e.getMessage())))
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(" Erreur de validation : " + e.getMessage())))
                .onErrorResume(Exception.class, e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(" Erreur inattendue : " + e.getMessage())));
    }
}
