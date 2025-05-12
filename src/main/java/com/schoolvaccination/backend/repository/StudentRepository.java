package com.schoolvaccination.backend.repository;

import com.schoolvaccination.backend.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface StudentRepository extends JpaRepository<Student, Long> {
    long countByVaccinated(boolean vaccinated);
    List<Student> findByVaccineName(String vaccineName, Pageable pageable);
}