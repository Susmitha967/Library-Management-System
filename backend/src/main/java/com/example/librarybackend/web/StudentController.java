package com.example.librarybackend.web;

import com.example.librarybackend.domain.Student;
import com.example.librarybackend.repo.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @PostMapping
  public ResponseEntity<String> create(
      @RequestParam String id,
      @RequestParam String name,
      @RequestParam String course,
      @RequestParam String branch,
      @RequestParam String semester
  ) {
    Student s = new Student();
    s.setId(id);
    s.setName(name);
    s.setCourse(course);
    s.setBranch(branch);
    s.setSemester(semester);

    studentRepository.save(s);
    return ResponseEntity.ok("OK");
  }
}

