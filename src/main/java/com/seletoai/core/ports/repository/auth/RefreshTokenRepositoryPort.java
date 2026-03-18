package com.seletoai.core.ports.repository.auth;

import com.seletoai.core.domain.auth.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {

  RefreshToken save(RefreshToken token);

  Optional<RefreshToken> findByToken(String token);

  void revokeAllByUserId(Integer userId);

  void deleteByUserId(Integer userId);
}