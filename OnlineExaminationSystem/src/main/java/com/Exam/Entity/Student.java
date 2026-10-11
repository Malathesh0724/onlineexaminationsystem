package com.Exam.Entity;

import java.util.ArrayList;
import java.util.List;

import com.Exam.Enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String department;

    private String password;

    private Role role = Role.STUDENT;

    private String profileImageName;

    @OneToMany(
        mappedBy = "student",
        cascade = CascadeType.ALL
    )
    private List<ExamAttempt> attempts = new ArrayList<>();
}
