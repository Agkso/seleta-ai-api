package com.seletoai.adapters.persistence.auth;

import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.core.ports.out.auth.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

  private final SpringRefreshTokenRepository repository;

  @Override
  public RefreshToken save(RefreshToken token) {
    return repository.save(token);
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return repository.findByToken(token);
  }

  @Override
  public void revokeAllByUserId(Long userId) {
    repository.revokeAllByUserId(userId);
  }

  @Override
  public void deleteByUserId(Long userId) {
    repository.deleteByUserId(userId);
  }
}