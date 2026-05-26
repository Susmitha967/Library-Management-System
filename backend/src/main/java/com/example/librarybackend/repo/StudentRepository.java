package com.example.librarybackend.repo;

import com.example.librarybackend.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}

