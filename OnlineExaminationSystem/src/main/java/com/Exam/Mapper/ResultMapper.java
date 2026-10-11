package com.Exam.Mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.Exam.DTO.ResultResponse;
import com.Exam.Entity.Result;

@Component
public class ResultMapper {
    private static final Logger logger = LoggerFactory.getLogger(ResultMapper.class);


    public ResultResponse toResponse(Result result) {


        logger.info("ResultMapper.toResponse started");

        ResultResponse response =
                new ResultResponse();

        response.setResultId(result.getId());

        response.setAttemptId(
                result.getAttempt().getId()
        );

        response.setStudentId(
                result.getAttempt()
                      .getStudent()
                      .getId()
        );

        response.setStudentName(
                result.getAttempt()
                      .getStudent()
                      .getName()
        );

        response.setExamId(
                result.getAttempt()
                      .getExam()
                      .getId()
        );

        response.setExamTitle(
                result.getAttempt()
                      .getExam()
                      .getTitle()
        );

        response.setTotalMarks(
                result.getTotalMarks()
        );

        response.setObtainedMarks(
                result.getObtainedMarks()
        );

        response.setPercentage(
                result.getPercentage()
        );

        return response;
    }
}
