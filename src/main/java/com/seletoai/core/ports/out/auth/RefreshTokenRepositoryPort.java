package com.seletoai.core.ports.out.auth;

import com.seletoai.core.domain.auth.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {

  RefreshToken save(RefreshToken token);

  Optional<RefreshToken> findByToken(String token);

  void revokeAllByUserId(Long userId);

  void deleteByUserId(Long userId);
}