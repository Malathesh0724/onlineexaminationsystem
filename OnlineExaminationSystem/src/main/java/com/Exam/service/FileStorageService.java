package com.Exam.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);


    private final Path fileStorageLocation;

    public FileStorageService(
            @Value("${file.upload-dir:uploads}") String uploadDir) {

        try {
            this.fileStorageLocation =
                    Paths.get(uploadDir)
                         .toAbsolutePath()
                         .normalize();

            Files.createDirectories(this.fileStorageLocation);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not create upload directory", e
            );
        }
    }

    public String storeFile(MultipartFile file) {

        logger.info("FileStorageService.storeFile started");

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File cannot be empty"
            );
        }

        String originalFileName =
                StringUtils.cleanPath(
                        file.getOriginalFilename()
                );

        if (originalFileName.isBlank()) {

            throw new IllegalArgumentException(
                    "Invalid file name"
            );
        }

        String fileName =
                UUID.randomUUID() + "_" + originalFileName;

        Path targetLocation =
                fileStorageLocation.resolve(fileName)
                                   .normalize();

        if (!targetLocation.getParent()
                .equals(fileStorageLocation)) {

            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        try (InputStream inputStream = file.getInputStream()) {

            Files.copy(
                    inputStream,
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not store file", e
            );
        }
    }

    public Path loadFile(String fileName) {

        logger.info("FileStorageService.loadFile started");

        Path filePath =
                fileStorageLocation.resolve(fileName)
                                   .normalize();

        if (!filePath.getParent()
                .equals(fileStorageLocation)) {

            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        if (!Files.exists(filePath)) {

            throw new RuntimeException(
                    "File not found: " + fileName
            );
        }

        return filePath;
    }

    public void deleteFile(String fileName) {

        logger.info("FileStorageService.deleteFile started");

        try {

            Files.deleteIfExists(
                    loadFile(fileName)
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not delete file", e
            );
        }
    }
}
