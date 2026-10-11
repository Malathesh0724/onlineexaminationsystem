package com.Exam.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exam.DTO.ResultResponse;
import com.Exam.service.ResultService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {
    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);


    private final ResultService resultService;

    @GetMapping("/{resultId}")
    public ResponseEntity<ResultResponse> getResult(
            @PathVariable Long resultId) {

        return ResponseEntity.ok(
                resultService.getResult(resultId)
        );
    }

    @GetMapping("/rankings/{examId}")
    public ResponseEntity<Page<ResultResponse>> getRankings(

            @PathVariable Long examId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        PageRequest pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                            Sort.Direction.DESC,
                            "obtainedMarks"
                        )
                );

        return ResponseEntity.ok(
                resultService.getRankings(
                        examId,
                        pageable
                )
        );
    }
}

