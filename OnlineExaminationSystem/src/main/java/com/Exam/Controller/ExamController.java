package com.Exam.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exam.DTO.ExamRequest;
import com.Exam.DTO.QuestionRequest;
import com.Exam.Entity.Exam;
import com.Exam.Entity.Question;
import com.Exam.service.ExamService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {
    private static final Logger logger = LoggerFactory.getLogger(ExamController.class);


    private final ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> createExam(
            @Valid @RequestBody ExamRequest request) {
        logger.info("ExamController.createExam started");

        Exam exam = examService.createExam(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exam);
    }

    @GetMapping
    public ResponseEntity<Page<Exam>> getAllExams(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy) {

        PageRequest pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending()
                );

        return ResponseEntity.ok(
                examService.getAllExams(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExam(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                examService.getExam(id)
        );
    }

    @PostMapping("/{examId}/questions")
    public ResponseEntity<Question> addQuestion(
            @PathVariable Long examId,
            @Valid @RequestBody QuestionRequest request) {
        logger.info("ExamController.addQuestion started");

        Question question =
                examService.addQuestion(
                        examId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(question);
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<Page<Question>> getQuestions(

            @PathVariable Long examId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        PageRequest pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                examService.getQuestions(
                        examId,
                        pageable
                )
        );
    }
}
