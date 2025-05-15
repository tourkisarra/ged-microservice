package com.example.ged_microservice.controller;

import com.example.ged_microservice.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping("/submit/{documentId}")
    public ResponseEntity<String> submit(@PathVariable String documentId) {
        try {
            workflowService.submitForApproval(documentId);
            return ResponseEntity.ok("Document soumis pour approbation");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(" Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/approve/{documentId}")
    public ResponseEntity<String> approve(@PathVariable String documentId) {
        try {
            workflowService.approveDocument(documentId);
            return ResponseEntity.ok(" Document approuvé");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(" Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/reject/{documentId}")
    public ResponseEntity<String> reject(@PathVariable String documentId) {
        try {
            workflowService.rejectDocument(documentId);
            return ResponseEntity.ok(" Document rejeté");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(" Erreur : " + e.getMessage());
        }
    }
}
