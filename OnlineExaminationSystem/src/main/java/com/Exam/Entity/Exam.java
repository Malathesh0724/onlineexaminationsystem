package com.Exam.Entity;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer durationMinutes;

    private Boolean active = true;

    @OneToMany(
        mappedBy = "exam",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Question> questions = new ArrayList<>();

    @OneToMany(
        mappedBy = "exam",
        cascade = CascadeType.ALL
    )
    private List<ExamAttempt> attempts = new ArrayList<>();
}
