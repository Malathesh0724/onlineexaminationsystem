package com.Exam.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Exam.DTO.ResultResponse;
import com.Exam.Entity.Result;
import com.Exam.Mapper.ResultMapper;
import com.Exam.Repository.ResultRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultService {
    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);


    private final ResultRepository resultRepository;
    private final ResultMapper resultMapper;

    @Transactional(readOnly = true)
    public ResultResponse getResult(Long resultId) {

        Result result =
                resultRepository.findById(resultId)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Result not found"
                        )
                    );

        return resultMapper.toResponse(result);
    }

    @Transactional(readOnly = true)
    public Page<ResultResponse> getRankings(
            Long examId,
            Pageable pageable) {

        return resultRepository
                .findByAttemptExamIdOrderByObtainedMarksDesc(
                    examId,
                    pageable
                )
                .map(resultMapper::toResponse);
    }
}
