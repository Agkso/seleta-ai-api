package com.seletoai.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

public final class AuthDTO {

  private AuthDTO() {}

  @Builder
  public record LoginRequest(
    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    String email,

    @NotBlank(message = "Senha é obrigatória.")
    String password
  ) {}

  @Builder
  public record RegisterRequest(
    @NotBlank(message = "Nome é obrigatório.")
    String name,

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    String email,

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres.")
    String password,

    @NotNull(message = "Role é obrigatória.")
    Long roleId,

    Long instituicaoId
  ) {}

  @Builder
  public record AuthResponse(
    String token,
    String refreshToken
  ) {}

  @Builder
  public record RefreshTokenRequest(
    Long userId,
    String token,
    LocalDateTime expiresAt,
    boolean revoked
  ) {}

  @Builder
  public record RefreshTokenResponse(
    Long id,
    Long userId,
    String token,
    LocalDateTime expiresAt,
    boolean revoked
  ) {}
}
