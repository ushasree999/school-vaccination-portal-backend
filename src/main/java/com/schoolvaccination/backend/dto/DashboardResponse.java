package com.schoolvaccination.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {
    private long totalStudents;
    private long vaccinatedStudents;
    private boolean hasUpcomingDrives;
}