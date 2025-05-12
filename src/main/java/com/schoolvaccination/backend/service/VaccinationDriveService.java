package com.schoolvaccination.backend.service;

import com.schoolvaccination.backend.entity.VaccinationDrive;
import com.schoolvaccination.backend.repository.VaccinationDriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccinationDriveService {


    private final VaccinationDriveRepository vaccinationDriveRepository;

    public VaccinationDriveService(VaccinationDriveRepository vaccinationDriveRepository) {
        this.vaccinationDriveRepository = vaccinationDriveRepository;
    }

    public VaccinationDrive createVaccinationDrive(VaccinationDrive drive) {
        drive.setApproved(false); // Default to not approved
        return vaccinationDriveRepository.save(drive);
    }

    public List<VaccinationDrive> getUpcomingVaccinationDrives() {
        LocalDate currentDate = LocalDate.now();
        return vaccinationDriveRepository.findAllByDateAfter(currentDate);
    }

    public VaccinationDrive updateVaccinationDrive(Long id, VaccinationDrive updatedDrive) {
        VaccinationDrive existingDrive = vaccinationDriveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccination drive not found"));
        if (existingDrive.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot edit completed vaccination drives");
        }
        existingDrive.setDate(updatedDrive.getDate());
        existingDrive.setNumberOfVaccines(updatedDrive.getNumberOfVaccines());
        existingDrive.setLocation(updatedDrive.getLocation());
        existingDrive.setApplicableGrades(updatedDrive.getApplicableGrades());
        existingDrive.setVaccineName(updatedDrive.getVaccineName()); // Update the vaccine name
        existingDrive.setApproved(updatedDrive.isApproved()); // Update the approved field
        return vaccinationDriveRepository.save(existingDrive);
    }
    // Get a list of all vaccination drives
    public List<VaccinationDrive> getAllVaccinationDrives() {
        return vaccinationDriveRepository.findAll();
    }
}