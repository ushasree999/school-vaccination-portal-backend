package com.schoolvaccination.backend.service;

import com.schoolvaccination.backend.dto.ReportResponse;
import com.schoolvaccination.backend.entity.Student;
import com.schoolvaccination.backend.repository.StudentRepository;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private StudentRepository studentRepository;

    public List<ReportResponse> getReport(String vaccineName, Pageable pageable) {
        List<Student> students = studentRepository.findByVaccineName(vaccineName, pageable);
        return students.stream()
                .map(student -> new ReportResponse(
                        student.getName(),
                        student.isVaccinated(),
                        student.getVaccinationDate(),
                        student.getVaccineName()))
                .collect(Collectors.toList());
    }

//    public ResponseEntity<byte[]> downloadReport(String vaccineName, Pageable pageable) {
//        List<Student> students = studentRepository.findByVaccineName(vaccineName, pageable);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintWriter writer = new PrintWriter(outputStream);
//        writer.println("Name,Vaccinated,Date of Vaccination,Name of Vaccine");
//        for (Student student : students) {
//            writer.printf("%s,%s,%s,%s%n",
//                    student.getName(),
//                    student.isVaccinated(),
//                    student.getVaccinationDate(),
//                    student.getVaccineName());
//        }
//        writer.flush();
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
//                .body(outputStream.toByteArray());
//    }

    public ResponseEntity<byte[]> downloadReport(String vaccineName, Pageable pageable) {
        List<Student> students = studentRepository.findByVaccineName(vaccineName, pageable);

        // Create a ByteArrayOutputStream to write CSV data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        // Write CSV header
        writer.println("Name,Vaccinated,Date of Vaccination,Name of Vaccine");

        // Write CSV rows for each student
        for (Student student : students) {
            writer.printf("%s,%s,%s,%s%n",
                    student.getName(),
                    student.isVaccinated(),
                    student.getVaccinationDate(),
                    student.getVaccineName());
        }

        writer.flush();

        // Return the CSV as a response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(outputStream.toByteArray());
    }
}