package com.Exam.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Exam.Entity.Exam;

public interface ExamRepository
        extends JpaRepository<Exam, Long> {

    long countByActiveTrue();
}