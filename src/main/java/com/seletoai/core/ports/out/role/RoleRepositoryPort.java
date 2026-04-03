package com.seletoai.core.ports.out.role;

import com.seletoai.core.domain.role.Role;

import java.util.Optional;
import java.util.List;

public interface RoleRepositoryPort {

  Optional<Role> findByName(String name);

  Optional<Role> findById(Long id);

  List<Role> findAll();
}