package com.example.librarybackend.repo;

import com.example.librarybackend.domain.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
  Optional<Book> findFirstByStudentid(String studentid);
}

