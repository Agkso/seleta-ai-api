package com.seletoai.dto.auth;

import lombok.Builder;

import java.time.LocalDateTime;

public final class AuthDTO {

  private AuthDTO() {}

  @Builder
  public record LoginRequest(
    String email,
    String password
  ) {}

  @Builder
  public record RegisterRequest(
    String name,
    String email,
    String password,
    Integer roleId
  ) {}

  @Builder
  public record AuthResponse(
    String token,
    String refreshToken
  ) {}

  @Builder
  public record RefreshTokenRequest(
    Integer userId,
    String token,
    LocalDateTime expiresAt,
    boolean revoked
  ) {}

  @Builder
  public record RefreshTokenResponse(
    Integer id,
    Integer userId,
    String token,
    LocalDateTime expiresAt,
    boolean revoked
  ) {}
}
