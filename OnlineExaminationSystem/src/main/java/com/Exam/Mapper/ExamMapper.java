package com.Exam.Mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.Exam.DTO.ExamRequest;
import com.Exam.Entity.Exam;

@Component
public class ExamMapper {
    private static final Logger logger = LoggerFactory.getLogger(ExamMapper.class);


    public Exam toEntity(ExamRequest request) {


        logger.info("ExamMapper.toEntity started");

        Exam exam = new Exam();

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setActive(request.getActive());

        return exam;
    }
}
