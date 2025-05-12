package com.schoolvaccination.backend.repository;

import com.schoolvaccination.backend.entity.VaccinationDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
public interface VaccinationDriveRepository extends JpaRepository<VaccinationDrive, Long> {
    boolean existsByDate(LocalDate date);
    @Query("SELECT d FROM VaccinationDrive d WHERE d.date > :date")
    List<VaccinationDrive> findAllByDateAfter(LocalDate date);
}