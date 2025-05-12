package com.schoolvaccination.backend.controller;

import com.schoolvaccination.backend.dto.ReportResponse;
import com.schoolvaccination.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getReport(
            @RequestParam(required = false) String vaccineName, Pageable pageable) {
        return ResponseEntity.ok(reportService.getReport(vaccineName, pageable));
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport(@RequestParam(required = false) String vaccineName, Pageable pageable) {
        return reportService.downloadReport(vaccineName, pageable);
    }
}