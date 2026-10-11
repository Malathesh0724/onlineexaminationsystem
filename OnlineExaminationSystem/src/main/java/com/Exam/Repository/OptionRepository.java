package com.Exam.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Exam.Entity.Option;

public interface OptionRepository
        extends JpaRepository<Option, Long> {

    List<Option> findByQuestionId(Long questionId);
}