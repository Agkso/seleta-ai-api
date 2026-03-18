package com.seletoai.core.mapper.auth;

import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.dto.auth.RefreshTokenRequestDTO;
import com.seletoai.dto.auth.RefreshTokenResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

  public RefreshToken toEntity(RefreshTokenRequestDTO dto) {
    if (dto == null) return null;

    RefreshToken entity = new RefreshToken();
    entity.setUserId(dto.getUserId());
    entity.setToken(dto.getToken());
    entity.setExpiresAt(dto.getExpiresAt());
    entity.setRevoked(dto.isRevoked());
    return entity;
  }

  public RefreshTokenResponseDTO toResponse(RefreshToken entity) {
    if (entity == null) return null;

    RefreshTokenResponseDTO dto = new RefreshTokenResponseDTO();
    dto.setId(entity.getId());
    dto.setUserId(entity.getUserId());
    dto.setToken(entity.getToken());
    dto.setExpiresAt(entity.getExpiresAt());
    dto.setRevoked(entity.isRevoked());
    return dto;
  }
}