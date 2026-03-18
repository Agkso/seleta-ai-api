package com.seletoai.core.useCase.user;

import com.seletoai.config.jwt.JwtService;
import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.core.ports.repository.auth.RefreshTokenRepositoryPort;
import com.seletoai.core.ports.repository.user.UserRepositoryPort;
import com.seletoai.dto.auth.AuthResponseDTO;
import com.seletoai.dto.login.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

  private final UserRepositoryPort repository;
  private final PasswordEncoder encoder;
  private final JwtService jwtService;
  private final RefreshTokenRepositoryPort refreshTokenRepository;

  public AuthResponseDTO execute(LoginRequestDTO request) {
    var user = repository
      .findByEmailAndDeletedAtIsNull(request.email())
      .orElseThrow(() -> new RuntimeException("User not found or inactive"));

    if (!encoder.matches(request.password(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String accessToken = jwtService.generateToken(user.getEmail());
    String refreshTokenValue = UUID.randomUUID().toString();

    refreshTokenRepository.revokeAllByUserId(user.getId());

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUserId(user.getId());
    refreshToken.setToken(refreshTokenValue);
    refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
    refreshToken.setRevoked(false);
    refreshTokenRepository.save(refreshToken);

    return new AuthResponseDTO(accessToken, refreshTokenValue);
  }
}