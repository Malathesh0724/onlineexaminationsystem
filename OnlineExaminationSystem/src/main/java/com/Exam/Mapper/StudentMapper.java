package com.Exam.Mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.Exam.DTO.StudentRequest;
import com.Exam.DTO.StudentResponse;
import com.Exam.Entity.Student;

@Component
public class StudentMapper {
    private static final Logger logger = LoggerFactory.getLogger(StudentMapper.class);


    public Student toEntity(StudentRequest request) {


        logger.info("StudentMapper.toEntity started");

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDepartment(request.getDepartment());
        student.setPassword(request.getPassword());

        return student;
    }

    public StudentResponse toResponse(Student student) {

        logger.info("StudentMapper.toResponse started");

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setDepartment(student.getDepartment());
        response.setRole(student.getRole());

        return response;
    }
}
