package com.Exam.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.Exam.service.FileStorageService;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(FileController.class)
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc(addFilters = false)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStorageService fileStorageService;

    @Test
    void shouldUploadFile() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.txt",
                        "text/plain",
                        "Hello".getBytes()
                );

        when(fileStorageService.storeFile(any(MockMultipartFile.class)))
                .thenReturn("generated_test.txt");

        mockMvc.perform(
                multipart("/api/files/upload")
                        .file(file)
        )
        .andExpect(status().isOk())
        .andExpect(
                content().string(
                        "File uploaded successfully: generated_test.txt"
                )
        );
    }
}
