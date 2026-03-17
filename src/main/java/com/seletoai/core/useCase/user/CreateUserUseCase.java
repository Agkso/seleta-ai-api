package com.seletoai.core.useCase.user;

import com.seletoai.core.domain.user.User;
import com.seletoai.core.domain.userRole.UserRole;
import com.seletoai.core.mapper.user.UserMapper;
import com.seletoai.core.mapper.userRole.UserRoleMapper;
import com.seletoai.core.ports.repository.*;
import com.seletoai.dto.register.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

  private final UserRepositoryPort userRepository;
  private final RoleRepositoryPort roleRepository;
  private final UserRoleRepositoryPort userRoleRepository;
  private final UserMapper userMapper;
  private final UserRoleMapper userRoleMapper;

  @Transactional
  public User execute(RegisterRequestDTO dto) {
    var role = roleRepository.findById(dto.roleId())
      .orElseThrow(() -> new RuntimeException("Role ID não encontrado."));

    User user = userMapper.toEntity(dto);
    User savedUser = userRepository.save(user);

    UserRole userRole = userRoleMapper.toEntity(savedUser.getId(), role.getId());
    userRoleRepository.save(userRole);

    return savedUser;
  }
}