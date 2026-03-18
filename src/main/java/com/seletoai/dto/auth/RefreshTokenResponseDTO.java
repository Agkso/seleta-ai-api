package com.seletoai.dto.auth;

import java.time.LocalDateTime;

public class RefreshTokenResponseDTO {

  private Integer id;
  private Integer userId;
  private String token;
  private LocalDateTime expiresAt;
  private boolean revoked;

  public Integer getId() { return id; }
  public void setId(Integer id) { this.id = id; }

  public Integer getUserId() { return userId; }
  public void setUserId(Integer userId) { this.userId = userId; }

  public String getToken() { return token; }
  public void setToken(String token) { this.token = token; }

  public LocalDateTime getExpiresAt() { return expiresAt; }
  public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

  public boolean isRevoked() { return revoked; }
  public void setRevoked(boolean revoked) { this.revoked = revoked; }
}