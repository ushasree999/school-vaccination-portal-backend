package com.schoolvaccination.backend.controller;

import com.schoolvaccination.backend.entity.VaccinationDrive;
import com.schoolvaccination.backend.service.VaccinationDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccination-drives")
public class VaccinationDriveController {

    @Autowired
    private VaccinationDriveService vaccinationDriveService;

    @PostMapping
    public ResponseEntity<VaccinationDrive> createVaccinationDrive(@RequestBody VaccinationDrive drive) {
        return ResponseEntity.ok(vaccinationDriveService.createVaccinationDrive(drive));
    }

    @GetMapping
    public ResponseEntity<List<VaccinationDrive>> getUpcomingVaccinationDrives() {
        return ResponseEntity.ok(vaccinationDriveService.getUpcomingVaccinationDrives());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccinationDrive> updateVaccinationDrive(
            @PathVariable Long id, @RequestBody VaccinationDrive drive) {
        return ResponseEntity.ok(vaccinationDriveService.updateVaccinationDrive(id, drive));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VaccinationDrive>> getAllVaccinationDrives() {
        return ResponseEntity.ok(vaccinationDriveService.getAllVaccinationDrives());
    }
}