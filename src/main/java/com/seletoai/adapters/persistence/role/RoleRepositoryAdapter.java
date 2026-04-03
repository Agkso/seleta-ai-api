package com.seletoai.adapters.persistence.role;

import com.seletoai.core.domain.role.Role;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

  private final SpringRoleRepository repository;

  @Override
  public Optional<Role> findByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public Optional<Role> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public List<Role> findAll() {
    return repository.findAll();
  }
}