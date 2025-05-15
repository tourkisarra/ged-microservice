package com.example.ged_microservice.service;

import com.example.ged_microservice.config.NuxeoWebClientConfig;
import com.example.ged_microservice.model.WorkflowStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

@Service
public class SecureUploadService {

    private static final Logger LOGGER = Logger.getLogger(SecureUploadService.class.getName());

    @Autowired private NuxeoWebClientConfig config;
    @Autowired private VaultKeyValueOperations kvOperations;
    @Autowired private DocumentIndexingService indexingService;

    private final Tika tika = new Tika();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<String> uploadSecureDocument(MultipartFile file, String parentPath, String title, String workspace) {
        if (file == null || file.isEmpty()) return Mono.error(new IllegalArgumentException("Fichier vide"));
        if (parentPath == null || parentPath.isBlank()) return Mono.error(new IllegalArgumentException("Chemin invalide"));
        if (title == null || title.isBlank()) return Mono.error(new IllegalArgumentException("Titre manquant"));

        return Mono.fromCallable(() -> {
            String mimeType = tika.detect(file.getInputStream());
            String content = tika.parseToString(file.getInputStream());

            File processedFile;
            if ("application/pdf".equals(mimeType)) processedFile = compressPDF(file);
            else if (mimeType.contains("word") || mimeType.contains("sheet")) processedFile = convertToTextFile(file);
            else if (mimeType.contains("jpeg") || mimeType.contains("png")) processedFile = compressImage(file);
            else throw new IllegalArgumentException("Format non supporté : " + mimeType);

            File encryptedFile = encryptFileAES(processedFile);
            String name = title.replace(" ", "_") + "_" + UUID.randomUUID();
            String json = "{ \"entity-type\": \"document\", \"name\": \"" + name + "\", \"type\": \"File\", \"properties\": {\"dc:title\": \"" + title + "\"}}";

            Map<String, Object> meta = new HashMap<>();
            meta.put("mimeType", mimeType);
            meta.put("content", content);
            meta.put("file", encryptedFile);
            meta.put("json", json);
            return meta;
        }).flatMap(meta -> config.getWebClient()
                .post()
                .uri("/path/" + parentPath)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(meta.get("json"))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(createResponse -> {
                    try {
                        String documentId = objectMapper.readTree(createResponse).get("uid").asText();
                        MultiValueMap<String, Object> multipart = new LinkedMultiValueMap<>();
                        multipart.add("file", new FileSystemResource((File) meta.get("file")));

                        return config.getWebClient()
                                .put()
                                .uri("/id/" + documentId + "/@blob/file:content")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .body(BodyInserters.fromMultipartData(multipart))
                                .retrieve()
                                .bodyToMono(String.class)
                                .map(uploadResponse -> {
                                    Map<String, Object> doc = new HashMap<>();
                                    doc.put("document_id", documentId);
                                    doc.put("title", title);
                                    doc.put("content", meta.get("content"));
                                    doc.put("file_type", meta.get("mimeType"));
                                    doc.put("created_date", Instant.now().toString());
                                    doc.put("storage_url", "/nuxeo/api/v1/id/" + documentId + "/@blob/file:content");
                                    doc.put("workspace", workspace);
                                    doc.put("status", WorkflowStatus.DRAFT.name());

                                    // ✅ Indexation immédiate
                                    indexingService.indexDocument(documentId, doc);

                                    return "Upload sécurisé réussi. Document ID : " + documentId;
                                });
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Erreur parsing JSON : " + e.getMessage(), e));
                    }
                }));
    }


    // Méthodes utilitaires (compression, conversion, chiffrement)

    private File compressPDF(MultipartFile multipartFile) throws IOException {
        try (PDDocument document = PDDocument.load(multipartFile.getInputStream())) {
            File compressed = File.createTempFile("compressed_", ".pdf");
            document.save(compressed);
            return compressed;
        }
    }

    private File compressImage(MultipartFile multipartFile) throws IOException {
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        File compressed = File.createTempFile("compressed_", multipartFile.getOriginalFilename());
        String format = multipartFile.getOriginalFilename().endsWith(".png") ? "PNG" : "JPEG";

        ImageWriter writer = ImageIO.getImageWritersByFormatName(format).next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(compressed)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if ("JPEG".equals(format)) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.5f);
            }
            writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
        return compressed;
    }

    private File convertToTextFile(MultipartFile file) throws IOException, TikaException {
        String content = tika.parseToString(file.getInputStream());
        File tempTxt = File.createTempFile("converted_", ".txt");
        Files.write(tempTxt.toPath(), content.getBytes());
        return tempTxt;
    }

    private File encryptFileAES(File inputFile) throws IOException {
        byte[] decodedKey;
        try {
            Map<String, Object> data = kvOperations.get("ged/aes-key").getData();
            if (data == null || !data.containsKey("key")) {
                throw new RuntimeException("Clé AES non trouvée dans Vault.");
            }

            String encodedKey = data.get("key").toString();
            decodedKey = Base64.getDecoder().decode(encodedKey);

            if (!(decodedKey.length == 16 || decodedKey.length == 24 || decodedKey.length == 32)) {
                throw new IllegalArgumentException("Clé AES invalide : " + decodedKey.length + " octets. Attendu : 16, 24 ou 32.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur récupération clé AES : " + e.getMessage(), e);
        }

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(decodedKey, "AES"));

            File encryptedFile = File.createTempFile("encrypted_", ".enc");

            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(encryptedFile);
                 CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

                byte[] buffer = new byte[8192];
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    cos.write(buffer, 0, read);
                }
            }

            return encryptedFile;

        } catch (Exception e) {
            throw new IOException("Erreur de chiffrement : " + e.getMessage(), e);
        }
    }

}
