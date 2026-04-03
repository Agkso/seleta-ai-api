package com.seletoai.core.ports.out.userRole;

import com.seletoai.core.domain.userRole.UserRole;
import java.util.List;

public interface UserRoleRepositoryPort {

  UserRole save(UserRole userRole);

  List<UserRole> findByUserId(Long userId);

  List<UserRole> findByRoleId(Long roleId);
}