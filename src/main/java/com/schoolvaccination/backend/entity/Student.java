package com.schoolvaccination.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Data
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Long id;

    @Column(name = "student_name", nullable = true)
    private String name;

    @Column(name = "grade", nullable = true)
    private String grade;

    @Column(name = "vaccinated", nullable = true)
    private boolean vaccinated;

    @Column(name = "vaccination_date", nullable = true)
    private LocalDate vaccinationDate;

    @Column(name = "vaccine_name", nullable = true)
    private String vaccineName;

}