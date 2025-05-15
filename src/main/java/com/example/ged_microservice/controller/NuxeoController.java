package com.example.ged_microservice.controller;
import com.example.ged_microservice.service.NuxeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import java.util.Map;
@RestController
@RequestMapping("/api/nuxeo")
public class NuxeoController {

    @Autowired
    private NuxeoService nuxeoService;

    @PostMapping("/create")
    public String createDocument(@RequestParam String parent, @RequestParam String title) {
        return nuxeoService.createDocument(parent, title);
    }

    @GetMapping("/get")
    public Mono<String> getDocument(@RequestParam String id) {
        return nuxeoService.getDocument(id);
    }
    @GetMapping("/children")
    public String listChildren(@RequestParam String parentPath) {
        return nuxeoService.listChildren(parentPath);
    }
    @DeleteMapping("/delete")
    public String deleteDocument(@RequestParam String id) {
        return nuxeoService.deleteDocument(id);
    }
    @PostMapping("/search")
    public String search(@RequestBody Map<String, String> body) {
        String keyword = body.get("keyword");
        return nuxeoService.searchDocuments(keyword);
    }
    @PostMapping("/upload")
    public String uploadFileToNuxeo(@RequestParam("file") MultipartFile file,
                                    @RequestParam("parentPath") String parentPath,
                                    @RequestParam("title") String title) {
        return nuxeoService.uploadFile(file, parentPath, title);
    }

}
