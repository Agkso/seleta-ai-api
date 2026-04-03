package com.seletoai.adapters.persistence.userRole;

import com.seletoai.core.domain.userRole.UserRole;
import com.seletoai.adapters.persistence.base.BaseRepository;

import java.util.List;

public interface SpringUserRoleRepository
  extends BaseRepository<UserRole> {

  List<UserRole> findByUserId(Long userId);

  List<UserRole> findByRoleId(Long roleId);
}