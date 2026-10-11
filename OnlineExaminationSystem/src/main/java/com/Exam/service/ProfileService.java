package com.Exam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Exam.Entity.Student;
import com.Exam.Repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    private final StudentRepository studentRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public Student uploadProfileImage(Long studentId, MultipartFile file) {
        logger.info("ProfileService.uploadProfileImage started for studentId={}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        String oldFileName = student.getProfileImageName();
        String newFileName = fileStorageService.storeFile(file);
        student.setProfileImageName(newFileName);
        Student savedStudent = studentRepository.save(student);

        if (oldFileName != null && !oldFileName.isBlank()) {
            try {
                fileStorageService.deleteFile(oldFileName);
            } catch (RuntimeException ex) {
                logger.warn("Could not delete previous profile image for studentId={}", studentId, ex);
            }
        }
        logger.info("Profile image uploaded for studentId={}", studentId);
        return savedStudent;
    }

    @Transactional(readOnly = true)
    public Student getProfile(Long studentId) {
        logger.info("ProfileService.getProfile started for studentId={}", studentId);
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    }

    @Transactional
    public void deleteProfileImage(Long studentId) {
        logger.info("ProfileService.deleteProfileImage started for studentId={}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        String fileName = student.getProfileImageName();
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Student does not have a profile image");
        }

        student.setProfileImageName(null);
        studentRepository.save(student);
        fileStorageService.deleteFile(fileName);
        logger.info("Profile image deleted for studentId={}", studentId);
    }
}
