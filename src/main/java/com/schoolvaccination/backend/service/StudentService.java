package com.schoolvaccination.backend.service;

import com.schoolvaccination.backend.dto.CreatedStudentResponseDto;
import com.schoolvaccination.backend.entity.Student;
import com.schoolvaccination.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public CreatedStudentResponseDto addStudent(Student student) {
        Student studentSaved = studentRepository.save(student);

        return CreatedStudentResponseDto.builder()
                .id(studentSaved.getId())
                .name(studentSaved.getName())
                .build();
    }

    @Transactional
    public void bulkUploadStudents(MultipartFile file) {
        List<Student> students = new ArrayList<>();
        System.out.println(LocalDate.of(2023, 10, 15));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header row
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim whitespace
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header row
                    continue;
                }
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                System.out.println("Processing line: " + line);
                String[] data = line.split(",");
                if (data.length < 5) {
                    throw new RuntimeException("Invalid data format in CSV: " + line);
                }

                // Validate and set fields
                String name = data[0].trim();
                if (name.isEmpty()) {
                    throw new RuntimeException("Name cannot be null or empty in line: " + line);
                }

                String vaccinatedStr = data[1].trim();
                Boolean vaccinated = null;
                if (!vaccinatedStr.isEmpty()) {
                    vaccinated = Boolean.parseBoolean(vaccinatedStr);
                }

                String vaccineName = data[2].trim();
                vaccineName = vaccineName.isEmpty() ? null : vaccineName;

                String vaccinationDateStr = data[3].trim();
                LocalDate vaccinationDate = null;
                if (!vaccinationDateStr.isEmpty()) {
                    vaccinationDate = LocalDate.parse(vaccinationDateStr);
                }

                String grade = data[4].trim();
                grade = grade.isEmpty() ? null : grade;

                // Create and add the student
                Student student = new Student();
                student.setName(name);
                student.setVaccinated(vaccinated != null && vaccinated); // Default to false if null
                student.setVaccineName(vaccineName);
                student.setVaccinationDate(vaccinationDate);
                student.setGrade(grade);
                students.add(student);
            }
            studentRepository.saveAll(students);
        } catch (Exception e) {
            throw new RuntimeException("Error during bulk upload", e);
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudentOpt = studentRepository.findById(id);
        if (existingStudentOpt.isPresent()) {
            Student existingStudent = existingStudentOpt.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setGrade(updatedStudent.getGrade());
            existingStudent.setVaccinated(updatedStudent.isVaccinated());
            return studentRepository.save(existingStudent);
        } else {
            throw new RuntimeException("Student with ID " + id + " not found");
        }
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with ID " + id + " not found"));
    }
}