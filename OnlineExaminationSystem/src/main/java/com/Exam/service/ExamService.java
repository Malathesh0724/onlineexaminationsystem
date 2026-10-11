package com.Exam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Exam.DTO.ExamRequest;
import com.Exam.DTO.OptionRequest;
import com.Exam.DTO.QuestionRequest;
import com.Exam.Entity.Exam;
import com.Exam.Entity.Option;
import com.Exam.Entity.Question;
import com.Exam.Mapper.ExamMapper;
import com.Exam.Repository.ExamRepository;
import com.Exam.Repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamService {

    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamMapper examMapper;

    @Transactional
    @CacheEvict(value = "exams", allEntries = true)
    public Exam createExam(ExamRequest request) {

        logger.info("Creating exam with title: {}", request.getTitle());
        Exam exam = examMapper.toEntity(request);

        return examRepository.save(exam);
    }

    @Transactional(readOnly = true)
    public Page<Exam> getAllExams(Pageable pageable) {

        return examRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "exams", key = "#id")
    public Exam getExam(Long id) {
        logger.debug("Fetching exam id {} from database (cache miss)", id);

        return examRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Exam not found with id: " + id
                    )
                );
    }

    @Transactional
    @CacheEvict(value = "exams", key = "#examId")
    public Question addQuestion(
            Long examId,
            QuestionRequest request) {
        logger.info("ExamService.addQuestion started");

        Exam exam = getExam(examId);

        Question question = new Question();

        question.setQuestionText(
                request.getQuestionText()
        );

        question.setMarks(
                request.getMarks()
        );

        question.setExam(exam);

        if (request.getOptions() != null) {

            for (OptionRequest optionRequest
                    : request.getOptions()) {

                Option option = new Option();

                option.setOptionText(
                        optionRequest.getOptionText()
                );

                option.setCorrect(
                        optionRequest.getCorrect()
                );

                option.setQuestion(question);

                question.getOptions().add(option);
            }
        }

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Page<Question> getQuestions(
            Long examId,
            Pageable pageable) {

        // Make sure exam exists
        getExam(examId);

        return questionRepository
                .findByExamId(examId, pageable);
    }
}
