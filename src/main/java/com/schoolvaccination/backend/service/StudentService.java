package com.schoolvaccination.backend.service;

import com.schoolvaccination.backend.dto.CreatedStudentResponseDto;
import com.schoolvaccination.backend.entity.Student;
import com.schoolvaccination.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    public void bulkUploadStudents(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Student> students = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student();
                student.setName(data[0]);
                student.setVaccinated(Boolean.parseBoolean(data[1]));
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