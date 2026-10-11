package com.Exam.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Exam.Entity.Question;

public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    Page<Question> findByExamId(
            Long examId,
            Pageable pageable
    );
}