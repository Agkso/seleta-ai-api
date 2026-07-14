package com.seletoai.core.useCase.user;

import com.seletoai.config.jwt.JwtService;
import com.seletoai.core.domain.auth.RefreshToken;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.domain.role.RoleNomes;
import com.seletoai.core.domain.user.User;
import com.seletoai.core.domain.userRole.UserRole;
import com.seletoai.core.mapper.user.UserMapper;
import com.seletoai.core.mapper.userRole.UserRoleMapper;
import com.seletoai.core.ports.in.user.CreateUserUseCasePort;
import com.seletoai.core.ports.out.auth.RefreshTokenRepositoryPort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import com.seletoai.core.ports.out.user.UserRepositoryPort;
import com.seletoai.core.ports.out.userRole.UserRoleRepositoryPort;
import com.seletoai.dto.auth.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserUseCasePort {

  private final UserRepositoryPort userRepository;
  private final RoleRepositoryPort roleRepository;
  private final UserRoleRepositoryPort userRoleRepository;
  private final InstituicaoRepositoryPort instituicaoRepository;
  private final UserMapper userMapper;
  private final UserRoleMapper userRoleMapper;
  private final JwtService jwtService;
  private final RefreshTokenRepositoryPort refreshTokenRepository;

  @Transactional
  public AuthDTO.AuthResponse execute(AuthDTO.RegisterRequest dto) {

    var role = roleRepository.findById(dto.roleId())
      .orElseThrow(() -> new RuntimeException("Role ID não encontrado."));

    Instituicao instituicao = null;
    if (dto.instituicaoId() != null) {
      instituicao = instituicaoRepository.findById(dto.instituicaoId())
        .orElseThrow(() -> new RegraNegocioException("Instituição não encontrada."));
    } else if (RoleNomes.CONTRATANTE.equalsIgnoreCase(role.getName())) {
      throw new RegraNegocioException("Usuários com papel CONTRATANTE precisam informar a instituição.");
    }

    User user = userMapper.toEntity(dto, instituicao);
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

    return new AuthDTO.AuthResponse(accessToken, refreshTokenValue);
  }
}