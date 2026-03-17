package com.seletoai.adapters.persistence.role;

import com.seletoai.core.domain.role.Role;
import com.seletoai.adapters.persistence.base.BaseRepository;

import java.util.Optional;

public interface SpringRoleRepository
  extends BaseRepository<Role> {

  Optional<Role> findByName(String name);
}