package com.seletoai.core.useCase.auth;

import com.seletoai.config.jwt.JwtService;
import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.core.ports.repository.auth.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

  private final RefreshTokenRepositoryPort repository;
  private final JwtService jwtService;

  public String execute(String refreshToken) {

    RefreshToken token = repository.findByToken(refreshToken)
      .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    if (!token.isValid()) {
      throw new RuntimeException("Expired or revoked token");
    }

    return jwtService.generateToken(token.getUserId().toString());
  }
}