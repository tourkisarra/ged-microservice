package com.example.ged_microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/*@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public void save(MultipartFile file, String uploader) throws IOException {
        Document doc = new Document();
        doc.setFileName(file.getOriginalFilename());
        doc.setFileType(file.getContentType());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setUploader(uploader);
        doc.setContent(file.getBytes());

        documentRepository.save(doc);
    }
}*/
