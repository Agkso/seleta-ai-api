package com.seletoai.adapters.persistence.auth;

import com.seletoai.core.domain.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringRefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

  Optional<RefreshToken> findByToken(String token);

  void deleteByUserId(Integer userId);
}