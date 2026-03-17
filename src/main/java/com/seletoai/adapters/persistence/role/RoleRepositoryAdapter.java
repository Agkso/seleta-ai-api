package com.seletoai.adapters.persistence.role;

import com.seletoai.core.domain.role.Role;
import com.seletoai.core.ports.repository.RoleRepositoryPort;
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
  public List<Role> findAllById(Iterable<Long> ids) {
    return repository.findAllById(ids);
  }
}