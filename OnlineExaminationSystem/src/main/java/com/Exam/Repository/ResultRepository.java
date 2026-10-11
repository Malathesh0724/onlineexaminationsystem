package com.Exam.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Exam.Entity.Result;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

    Page<Result> findByAttemptExamIdOrderByObtainedMarksDesc(
            Long examId,
            Pageable pageable
    );
}