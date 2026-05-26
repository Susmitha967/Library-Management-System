package com.example.librarybackend.web;

import com.example.librarybackend.repo.LoginRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final LoginRepository loginRepository;

  public AuthController(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestParam String userid,
      @RequestParam String password
  ) {
    String u = userid == null ? "" : userid.trim();
    String p = password == null ? "" : password.trim();

    return loginRepository.findById(u)
        .filter(c -> p.equals(c.getPassword() == null ? "" : c.getPassword().trim()))
        .map(c -> ResponseEntity.ok("OK"))
        .orElseGet(() -> ResponseEntity.status(401).body("INVALID"));
  }
}

