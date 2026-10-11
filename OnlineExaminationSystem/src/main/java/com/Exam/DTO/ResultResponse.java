package com.Exam.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private Long resultId;

    private Long attemptId;

    private Long studentId;

    private String studentName;

    private Long examId;

    private String examTitle;

    private Integer totalMarks;

    private Integer obtainedMarks;

    private Double percentage;
}