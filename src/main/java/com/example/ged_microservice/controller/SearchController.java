package com.example.ged_microservice.controller;

import com.example.ged_microservice.model.IndexedDocument;
import com.example.ged_microservice.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/es")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService service;

    // 📥 Indexer un document
    @PostMapping("/index")
    public ResponseEntity<IndexedDocument> indexDocument(@RequestBody IndexedDocument doc) {
        return ResponseEntity.ok(service.indexDocument(doc));
    }

    // 🔍 Recherche par mot-clé
    @GetMapping("/search")
    public ResponseEntity<List<IndexedDocument>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }

    // ❌ Supprimer par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // 🔎 Trouver par ID
    @GetMapping("/{id}")
    public ResponseEntity<IndexedDocument> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
