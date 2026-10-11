package com.Exam.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.Exam.Entity.Student;
import com.Exam.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getProfile(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                profileService.getProfile(studentId));
    }

    @PostMapping("/{studentId}/image")
    public ResponseEntity<Student> uploadProfileImage(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                profileService.uploadProfileImage(studentId, file));
    }

    @DeleteMapping("/{studentId}/image")
    public ResponseEntity<String> deleteProfileImage(
            @PathVariable Long studentId) {

        profileService.deleteProfileImage(studentId);
        return ResponseEntity.ok("Profile image deleted successfully");
    }
}