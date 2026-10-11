package com.Exam.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Exam.DTO.SubmitAnswerRequest;
import com.Exam.Entity.Answer;
import com.Exam.Entity.Exam;
import com.Exam.Entity.ExamAttempt;
import com.Exam.Entity.Option;
import com.Exam.Entity.Question;
import com.Exam.Entity.Result;
import com.Exam.Entity.Student;
import com.Exam.Enums.AttemptStatus;
import com.Exam.Repository.ExamAttemptRepository;
import com.Exam.Repository.ExamRepository;
import com.Exam.Repository.OptionRepository;
import com.Exam.Repository.QuestionRepository;
import com.Exam.Repository.ResultRepository;
import com.Exam.Repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamAttemptService {
    private static final Logger logger = LoggerFactory.getLogger(ExamAttemptService.class);


    private final ExamAttemptRepository attemptRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final ResultRepository resultRepository;

    @Transactional
    public ExamAttempt startExam(
            Long studentId,
            Long examId) {
        logger.info("ExamAttemptService.startExam started");

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Student not found"
                    )
                );

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Exam not found"
                    )
                );

        if (!Boolean.TRUE.equals(exam.getActive())) {
            throw new RuntimeException(
                    "Exam is not active"
            );
        }

        boolean alreadyAttempted =
                attemptRepository
                    .existsByExamIdAndStudentIdAndStatus(
                        examId,
                        studentId,
                        AttemptStatus.STARTED
                    );

        if (alreadyAttempted) {
            throw new RuntimeException(
                    "Student already has an active attempt"
            );
        }

        ExamAttempt attempt = new ExamAttempt();

        attempt.setStudent(student);
        attempt.setExam(exam);
        attempt.setStartedAt(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.STARTED);
        attempt.setScore(0);

        return attemptRepository.save(attempt);
    }

    @Transactional
    public Result submitExam(
            Long studentId,
            Long attemptId,
            List<SubmitAnswerRequest> requests) {
        logger.info("ExamAttemptService.submitExam started");

        ExamAttempt attempt =
                attemptRepository
                    .findByIdAndStudentId(
                        attemptId,
                        studentId
                    )
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Attempt not found"
                        )
                    );

        if (attempt.getStatus()
                == AttemptStatus.SUBMITTED) {

            throw new RuntimeException(
                    "Exam already submitted"
            );
        }

        validateExamTime(attempt);

        int totalMarks = 0;
        int obtainedMarks = 0;

        for (SubmitAnswerRequest request : requests) {

            Question question =
                    questionRepository
                        .findById(request.getQuestionId())
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Question not found"
                            )
                        );

            if (!question.getExam().getId()
                    .equals(attempt.getExam().getId())) {

                throw new RuntimeException(
                        "Question does not belong to this exam"
                );
            }

            Option selectedOption =
                    optionRepository
                        .findById(
                            request.getSelectedOptionId()
                        )
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Option not found"
                            )
                        );

            if (!selectedOption.getQuestion().getId()
                    .equals(question.getId())) {

                throw new RuntimeException(
                        "Selected option does not belong to question"
                );
            }

            boolean correct =
                    Boolean.TRUE.equals(
                        selectedOption.getCorrect()
                    );

            int marksAwarded =
                    correct ? question.getMarks() : 0;

            Answer answer = new Answer();

            answer.setAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedOption(selectedOption);
            answer.setCorrect(correct);
            answer.setMarksAwarded(marksAwarded);

            attempt.getAnswers().add(answer);

            totalMarks += question.getMarks();

            obtainedMarks += marksAwarded;
        }

        double percentage =
                totalMarks == 0
                    ? 0
                    : ((double) obtainedMarks
                        / totalMarks) * 100;

        attempt.setScore(obtainedMarks);
        attempt.setStatus(AttemptStatus.SUBMITTED);
        attempt.setSubmittedAt(LocalDateTime.now());

        ExamAttempt savedAttempt =
                attemptRepository.save(attempt);

        Result result = new Result();

        result.setAttempt(savedAttempt);
        result.setTotalMarks(totalMarks);
        result.setObtainedMarks(obtainedMarks);
        result.setPercentage(percentage);
        result.setCalculatedAt(LocalDateTime.now());

        return resultRepository.save(result);
    }

    private void validateExamTime(
            ExamAttempt attempt) {

        logger.info("ExamAttemptService.validateExamTime started");

        LocalDateTime startTime =
                attempt.getStartedAt();

        LocalDateTime currentTime =
                LocalDateTime.now();

        long elapsedMinutes =
                Duration.between(
                    startTime,
                    currentTime
                ).toMinutes();

        int allowedMinutes =
                attempt.getExam()
                       .getDurationMinutes();

        if (elapsedMinutes > allowedMinutes) {

            throw new RuntimeException(
                    "Exam time has expired"
            );
        }
    }
}
