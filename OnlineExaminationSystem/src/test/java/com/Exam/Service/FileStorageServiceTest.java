package com.Exam.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import com.Exam.service.FileStorageService;

class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldUploadAndLoadFile() {

        FileStorageService service =
                new FileStorageService(tempDir.toString());

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.txt",
                        "text/plain",
                        "Hello Spring Boot".getBytes()
                );

        String fileName = service.storeFile(file);

        assertNotNull(fileName);

        Path storedFile = service.loadFile(fileName);

        assertNotNull(storedFile);
        assertTrue(Files.exists(storedFile));
    }

    @Test
    void shouldRejectEmptyFile() {

        FileStorageService service =
                new FileStorageService(tempDir.toString());

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "empty.txt",
                        "text/plain",
                        new byte[0]
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.storeFile(file)
        );
    }

    @Test
    void shouldDeleteFile() {

        FileStorageService service =
                new FileStorageService(tempDir.toString());

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "delete.txt",
                        "text/plain",
                        "Delete me".getBytes()
                );

        String fileName = service.storeFile(file);

        Path storedFile = service.loadFile(fileName);

        service.deleteFile(fileName);

        assertFalse(Files.exists(storedFile));

        assertThrows(
                RuntimeException.class,
                () -> service.loadFile(fileName)
        );
    }
}