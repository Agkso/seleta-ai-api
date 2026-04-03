package com.seletoai.core.mapper.auth;

import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.dto.auth.AuthDTO;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

  public RefreshToken toEntity(AuthDTO.RefreshTokenRequest dto) {
    if (dto == null) {
      return null;
    }

    RefreshToken entity = new RefreshToken();
    entity.setUserId(dto.userId());
    entity.setToken(dto.token());
    entity.setExpiresAt(dto.expiresAt());
    entity.setRevoked(dto.revoked());
    return entity;
  }

  public AuthDTO.RefreshTokenResponse toResponse(RefreshToken entity) {
    if (entity == null) {
      return null;
    }

    return AuthDTO.RefreshTokenResponse.builder()
      .id(entity.getId())
      .userId(entity.getUserId())
      .token(entity.getToken())
      .expiresAt(entity.getExpiresAt())
      .revoked(entity.isRevoked())
      .build();
  }
}
