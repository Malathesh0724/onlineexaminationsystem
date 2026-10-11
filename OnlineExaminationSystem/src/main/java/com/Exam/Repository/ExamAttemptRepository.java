package com.Exam.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Exam.Entity.ExamAttempt;
import com.Exam.Enums.AttemptStatus;

public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    Optional<ExamAttempt> findByIdAndStudentId(
            Long attemptId,
            Long studentId
    );

    boolean existsByExamIdAndStudentIdAndStatus(
            Long examId,
            Long studentId,
            AttemptStatus status
    );
}
