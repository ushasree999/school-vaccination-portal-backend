package com.schoolvaccination.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "vaccination_drive")
public class VaccinationDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = true)
    private Long id;

    @Column(name = "vaccine_name", nullable = true)
    private String vaccineName;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "number_of_vaccines", nullable = true)
    private int numberOfVaccines;

    @Column(name = "location", nullable = true)
    private String location;

    @Column(name = "approved", nullable = true)
    private boolean isApproved;


    @Column(name = "applicable_grades", nullable = true)
    private String applicableGrades;
}