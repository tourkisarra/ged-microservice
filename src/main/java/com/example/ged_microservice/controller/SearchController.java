package com.example.ged_microservice.controller;

import com.example.ged_microservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    // 🔍 Recherche intelligente multi-critères
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String workspace,
            @RequestParam(required = false) String status,

            @RequestParam(required = false) String username
    ) {
        try {
            List<Map<String, Object>> results = searchService.searchDocuments(keyword, type, date, workspace,status, username);
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur de recherche : " + e.getMessage());
        }
    }

    // 💡 Suggestions automatiques (autocomplete)
    @GetMapping("/suggest")
    public ResponseEntity<?> suggest(@RequestParam String keyword) {
        try {
            List<String> suggestions = searchService.getSuggestions(keyword);
            return ResponseEntity.ok(suggestions);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur de suggestion : " + e.getMessage());
        }
    }

    // 📜 Historique utilisateur
    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestParam String username) {
        List<String> history = searchService.getSearchHistory(username);
        return ResponseEntity.ok(history);
    }
} //
