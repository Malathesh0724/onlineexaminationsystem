package com.Exam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Exam.Repository.ExamRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExamMonitoringScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(ExamMonitoringScheduler.class);

    private final ExamRepository examRepository;

    @Scheduled(
        fixedDelayString =
            "${app.scheduler.active-exam-log-delay-ms:60000}"
    )
    public void logActiveExamCount() {

        long activeExams = examRepository.countByActiveTrue();

        logger.info(
            "Scheduled monitoring: {} active exams",
            activeExams
        );
    }
}