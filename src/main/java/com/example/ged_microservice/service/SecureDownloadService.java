package com.example.ged_microservice.service;

import com.example.ged_microservice.config.NuxeoWebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;

@Service
public class SecureDownloadService {

    @Autowired
    private NuxeoWebClientConfig config;

    @Autowired
    private VaultKeyValueOperations kvOperations;

    public Resource downloadAndDecrypt(String documentId) {
        try {
            // 1. Fichier temporaire pour stocker le fichier chiffré
            File encryptedFile = File.createTempFile("downloaded_encrypted_", ".enc");

            // 2. Télécharger depuis Nuxeo (stream)
            try (OutputStream outputStream = new FileOutputStream(encryptedFile)) {
                Flux<DataBuffer> dataBufferFlux = config.getWebClient()
                        .get()
                        .uri("/id/" + documentId + "/@blob/file:content")
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .retrieve()
                        .bodyToFlux(DataBuffer.class);

                dataBufferFlux.toStream().forEach(buffer -> {
                    try (InputStream inputStream = buffer.asInputStream()) {
                        inputStream.transferTo(outputStream);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
            }

            // 3. Déchiffrer et retourner le fichier
            File decrypted = decryptAES(encryptedFile, documentId + "_decrypted.pdf");
            return new FileSystemResource(decrypted);

        } catch (Exception e) {
            throw new RuntimeException(" Erreur lors du téléchargement sécurisé : " + e.getMessage(), e);
        }
    }

    private File decryptAES(File encryptedFile, String outputFileName) throws Exception {
        // Récupération de la clé AES depuis Vault
        Map<String, Object> data = kvOperations.get("ged/aes-key").getData();
        if (data == null || !data.containsKey("key")) {
            throw new IllegalStateException("Clé AES introuvable dans Vault à l'emplacement secret/ged/aes-key");
        }

        String secretKey = data.get("key").toString();
        if (secretKey.length() != 16 && secretKey.length() != 24 && secretKey.length() != 32) {
            throw new IllegalArgumentException("Clé AES invalide : la longueur doit être 16, 24 ou 32 caractères.");
        }

        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        File tempFile = File.createTempFile(outputFileName, null);

        try (
                CipherInputStream cis = new CipherInputStream(new FileInputStream(encryptedFile), cipher);
                FileOutputStream fos = new FileOutputStream(tempFile)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }
}
