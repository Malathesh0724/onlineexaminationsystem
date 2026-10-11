package com.Exam.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exam.DTO.SubmitAnswerRequest;
import com.Exam.Entity.ExamAttempt;
import com.Exam.Entity.Result;
import com.Exam.service.ExamAttemptService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class ExamAttemptController {
    private static final Logger logger = LoggerFactory.getLogger(ExamAttemptController.class);


    private final ExamAttemptService examAttemptService;

    @PostMapping("/start")
    public ResponseEntity<ExamAttempt> startExam(

            @RequestParam Long studentId,

            @RequestParam Long examId) {
        logger.info("ExamAttemptController.startExam started");

        ExamAttempt attempt =
                examAttemptService.startExam(
                        studentId,
                        examId
                );

        return ResponseEntity.ok(attempt);
    }
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<Result> submitExam(

            @PathVariable Long attemptId,

            @RequestParam Long studentId,

            @Valid @RequestBody
            List<SubmitAnswerRequest> answers) {
        logger.info("ExamAttemptController.submitExam started");

        Result result =
                examAttemptService.submitExam(
                        studentId,
                        attemptId,
                        answers
                );

        return ResponseEntity.ok(result);
    }
}
