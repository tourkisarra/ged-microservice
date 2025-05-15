package com.example.ged_microservice.controller;

import com.example.ged_microservice.service.VersioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/versioning")
public class VersioningController {

    @Autowired
    private VersioningService versioningService;

    @PostMapping("/create/{documentId}")
    public Mono<ResponseEntity<String>> createVersion(@PathVariable String documentId) {
        return versioningService.createMinorVersion(documentId)
                .map(result -> ResponseEntity.ok("Version créée: " + result))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(" Erreur: " + e.getMessage())));
    }

    @GetMapping("/list/{documentId}")
    public Mono<ResponseEntity<String>> listVersions(@PathVariable String documentId) {
        return versioningService.listVersions(documentId)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(" Erreur: " + e.getMessage())));
    }

    @GetMapping("/download/{versionId}")
    public Mono<ResponseEntity<Resource>> downloadVersion(@PathVariable String versionId) {
        return versioningService.downloadVersion(versionId)
                .map(resource -> ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=version-" + versionId + ".bin")
                        .body(resource))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }
}
