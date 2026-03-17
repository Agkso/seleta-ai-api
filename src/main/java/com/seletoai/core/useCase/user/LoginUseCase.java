package com.seletoai.core.useCase;

import com.seletoai.config.jwt.JwtService;
import com.seletoai.core.ports.repository.UserRepositoryPort;
import com.seletoai.dto.auth.AuthResponseDTO;
import com.seletoai.dto.login.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

  private final UserRepositoryPort repository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;

  public AuthResponseDTO execute(LoginRequestDTO request) {
    var user = repository
      .findByEmailAndDeletedAtIsNull(request.email())
      .orElseThrow(() -> new RuntimeException("User not found or inactive"));

    if (!encoder.matches(request.password(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwtService.generateToken(user.getEmail());

    return new AuthResponseDTO(token);
  }
}