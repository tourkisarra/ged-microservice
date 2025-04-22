package com.example.ged_microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*@RestController
@RequestMapping("/api/docs")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('app-user','app-admin')")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) throws IOException {
        String uploader = authentication.getName();
        documentService.save(file, uploader);
        return ResponseEntity.ok(" Fichier enregistré temporairement.");
    }
}*/
