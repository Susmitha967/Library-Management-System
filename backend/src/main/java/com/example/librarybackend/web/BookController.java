package com.example.librarybackend.web;

import com.example.librarybackend.domain.Book;
import com.example.librarybackend.repo.BookRepository;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {
  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @PostMapping
  public ResponseEntity<String> create(
      @RequestParam String id,
      @RequestParam String name,
      @RequestParam String publisher,
      @RequestParam String price,
      @RequestParam String year
  ) {
    Book b = new Book();
    b.setId(id);
    b.setName(name);
    b.setPublisher(publisher);
    b.setPrice(price);
    b.setYear(year);
    b.setStatus("NotIssued");
    b.setIssue("");
    b.setDue("");
    b.setStudentid("");

    bookRepository.save(b);
    return ResponseEntity.ok("OK");
  }

  @GetMapping
  public ResponseEntity<String> getById(@RequestParam String id) {
    Optional<Book> b = bookRepository.findById(id);
    return b.map(this::toKeyValueBody)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(404).body("NOT_FOUND"));
  }

  @GetMapping("/by-student")
  public ResponseEntity<String> getByStudent(@RequestParam String studentId) {
    Optional<Book> b = bookRepository.findFirstByStudentid(studentId);
    return b.map(this::toKeyValueBody)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(404).body("NOT_FOUND"));
  }

  @PostMapping("/issue")
  public ResponseEntity<String> issue(
      @RequestParam String id,
      @RequestParam String studentId,
      @RequestParam String issue,
      @RequestParam String due
  ) {
    Book b = bookRepository.findById(id).orElse(null);
    if (b == null) return ResponseEntity.status(404).body("NOT_FOUND");

    b.setStatus("Issued");
    b.setStudentid(studentId);
    b.setIssue(issue);
    b.setDue(due);
    bookRepository.save(b);
    return ResponseEntity.ok("OK");
  }

  @PostMapping("/return")
  public ResponseEntity<String> returnBook(@RequestParam String id) {
    Book b = bookRepository.findById(id).orElse(null);
    if (b == null) return ResponseEntity.status(404).body("NOT_FOUND");

    b.setStatus("NotIssued");
    b.setIssue("");
    b.setDue("");
    b.setStudentid("");
    bookRepository.save(b);
    return ResponseEntity.ok("OK");
  }

  private String toKeyValueBody(Book b) {
    // simple format so Swing can parse without JSON libs
    return "id=" + safe(b.getId()) + "\n"
        + "name=" + safe(b.getName()) + "\n"
        + "publisher=" + safe(b.getPublisher()) + "\n"
        + "price=" + safe(b.getPrice()) + "\n"
        + "year=" + safe(b.getYear()) + "\n"
        + "status=" + safe(b.getStatus()) + "\n"
        + "issue=" + safe(b.getIssue()) + "\n"
        + "due=" + safe(b.getDue()) + "\n"
        + "studentid=" + safe(b.getStudentid()) + "\n";
  }

  private String safe(String s) {
    return s == null ? "" : s.replace("\n", " ").replace("\r", " ");
  }
}

