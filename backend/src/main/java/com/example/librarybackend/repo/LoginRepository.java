package com.example.librarybackend.repo;

import com.example.librarybackend.domain.LoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginCredential, String> {
}

