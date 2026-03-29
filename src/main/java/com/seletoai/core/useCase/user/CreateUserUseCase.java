package com.seletoai.core.useCase.user;

import com.seletoai.config.jwt.JwtService;
import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.core.domain.user.User;
import com.seletoai.core.domain.userRole.UserRole;
import com.seletoai.core.mapper.user.UserMapper;
import com.seletoai.core.mapper.userRole.UserRoleMapper;
import com.seletoai.core.ports.out.auth.RefreshTokenRepositoryPort;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import com.seletoai.core.ports.out.user.UserRepositoryPort;
import com.seletoai.core.ports.out.userRole.UserRoleRepositoryPort;
import com.seletoai.dto.auth.AuthResponseDTO;
import com.seletoai.dto.register.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

  private final UserRepositoryPort userRepository;
  private final RoleRepositoryPort roleRepository;
  private final UserRoleRepositoryPort userRoleRepository;
  private final UserMapper userMapper;
  private final UserRoleMapper userRoleMapper;
  private final JwtService jwtService;
  private final RefreshTokenRepositoryPort refreshTokenRepository;

  @Transactional
  public AuthResponseDTO execute(RegisterRequestDTO dto) {

    var role = roleRepository.findById(dto.roleId())
      .orElseThrow(() -> new RuntimeException("Role ID não encontrado."));

    User user = userMapper.toEntity(dto);
    User savedUser = userRepository.save(user);

    UserRole userRole = userRoleMapper.toEntity(savedUser.getId(), role.getId());
    userRoleRepository.save(userRole);

    String accessToken = jwtService.generateToken(savedUser.getEmail());
    String refreshTokenValue = UUID.randomUUID().toString();

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUserId(savedUser.getId());
    refreshToken.setToken(refreshTokenValue);
    refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
    refreshToken.setRevoked(false);
    refreshTokenRepository.save(refreshToken);

    return new AuthResponseDTO(accessToken, refreshTokenValue);
  }
}