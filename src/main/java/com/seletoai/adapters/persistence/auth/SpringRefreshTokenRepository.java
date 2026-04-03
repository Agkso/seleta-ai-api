package com.seletoai.adapters.persistence.auth;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.auth.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringRefreshTokenRepository extends BaseRepository<RefreshToken> {

  Optional<RefreshToken> findByToken(String token);

  void deleteByUserId(Long userId);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("update RefreshToken r set r.revoked = true where r.userId = :userId")
  void revokeAllByUserId(@Param("userId") Long userId);
}
