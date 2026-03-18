package com.seletoai.core.useCase.role;

import com.seletoai.core.domain.role.Role;
import com.seletoai.core.ports.repository.role.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesUseCase {

  private final RoleRepositoryPort roleRepository;

  public List<Role> execute() {
    return roleRepository.findAll();
  }
}