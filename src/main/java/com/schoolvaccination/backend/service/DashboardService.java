package com.schoolvaccination.backend.service;

import com.schoolvaccination.backend.dto.DashboardResponse;
import com.schoolvaccination.backend.repository.StudentRepository;
import com.schoolvaccination.backend.repository.VaccinationDriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccinationDriveRepository vaccinationDriveRepository;

    public DashboardResponse getDashboardData() {
        long totalStudents = studentRepository.count();
        long vaccinatedStudents = studentRepository.countByVaccinated(true);
        boolean hasUpcomingDrives = !vaccinationDriveRepository.findAllByDateAfter(LocalDate.now()).isEmpty();

        return new DashboardResponse(totalStudents, vaccinatedStudents, hasUpcomingDrives);
    }
}