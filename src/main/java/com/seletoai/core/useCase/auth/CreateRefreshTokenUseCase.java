package com.seletoai.core.useCase.auth;

import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.core.ports.in.auth.CreateRefreshTokenUseCasePort;
import com.seletoai.core.ports.out.auth.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateRefreshTokenUseCase implements CreateRefreshTokenUseCasePort {

  private final RefreshTokenRepositoryPort repository;

  public RefreshToken execute(Integer userId) {

    repository.deleteByUserId(userId);

    RefreshToken token = new RefreshToken();
    token.setUserId(userId);
    token.setToken(UUID.randomUUID().toString());
    token.setExpiresAt(LocalDateTime.now().plusDays(7));
    token.setRevoked(false);

    return repository.save(token);
  }
}