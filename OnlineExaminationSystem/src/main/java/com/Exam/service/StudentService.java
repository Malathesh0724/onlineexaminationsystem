package com.Exam.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Exam.DTO.StudentRequest;
import com.Exam.DTO.StudentResponse;
import com.Exam.Entity.Student;
import com.Exam.Mapper.StudentMapper;
import com.Exam.Repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);


    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        logger.info("StudentService.createStudent started");

        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "Email already registered"
            );
        }

        Student student = studentMapper.toEntity(request);

        // Encrypt password before storing in database
        student.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        Student savedStudent =
                studentRepository.save(student);

        return studentMapper.toResponse(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Student not found with id: " + id
                    )
                );

        return studentMapper.toResponse(student);
    }

    @Transactional
    public StudentResponse updateStudent(
            Long id,
            StudentRequest request) {
        logger.info("StudentService.updateStudent started");

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Student not found with id: " + id
                    )
                );

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDepartment(request.getDepartment());

        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            student.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );
        }

        Student updatedStudent =
                studentRepository.save(student);

        return studentMapper.toResponse(updatedStudent);
    }
}
