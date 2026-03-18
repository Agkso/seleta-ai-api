package com.seletoai.core.domain.auth;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken extends BaseEntity {

  @Column(nullable = false)
  private Integer userId;

  @Column(unique = true, nullable = false)
  private String token;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  private boolean revoked;

  public boolean isValid() {
    return !revoked && expiresAt.isAfter(LocalDateTime.now());
  }
}