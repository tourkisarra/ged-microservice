package com.example.ged_microservice.controller;

import com.example.ged_microservice.service.SecureDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class SecureDownloadController {

    @Autowired
    private SecureDownloadService secureDownloadService;

    @GetMapping("/downloadsecure/{id}")
    public ResponseEntity<Resource> downloadSecure(@PathVariable String id) {
        Resource file = secureDownloadService.downloadAndDecrypt(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
