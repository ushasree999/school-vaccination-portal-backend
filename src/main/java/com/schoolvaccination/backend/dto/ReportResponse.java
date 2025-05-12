package com.schoolvaccination.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReportResponse {
    private String name;
    private boolean vaccinated;
    private LocalDate vaccinationDate;
    private String vaccineName;
}