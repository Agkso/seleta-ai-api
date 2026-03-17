package com.seletoai.core.ports.repository;

import com.seletoai.core.domain.role.Role;

import java.util.Optional;
import java.util.List;

public interface RoleRepositoryPort {

  Optional<Role> findByName(String name);

  Optional<Role> findById(Integer integer);

  List<Role> findAll();
}