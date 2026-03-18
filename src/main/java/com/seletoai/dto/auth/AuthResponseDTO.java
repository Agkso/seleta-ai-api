package com.seletoai.dto.auth;

public record AuthResponseDTO(
  String token,
  String refreshToken
) {}