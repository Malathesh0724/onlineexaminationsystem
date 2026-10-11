package com.Exam.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Exam.Entity.Student;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    // JPQL query: uses the Student entity and department field.
    @Query("""
        SELECT s FROM Student s
        WHERE LOWER(s.department) = LOWER(:department)
        """)
    List<Student> findByDepartmentJpql(
        @Param("department") String department
    );

    // Native SQL query: uses the actual database table.
    @Query(
        value = "SELECT * FROM students WHERE department = :department",
        nativeQuery = true
    )
    List<Student> findByDepartmentNative(
        @Param("department") String department
    );
}