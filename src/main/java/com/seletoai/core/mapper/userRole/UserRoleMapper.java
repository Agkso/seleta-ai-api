package com.seletoai.core.mapper.userRole;

import com.seletoai.core.domain.userRole.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper {

  public UserRole toEntity(Integer userId, Integer roleId) {
    UserRole userRole = new UserRole();
    userRole.setUserId(userId);
    userRole.setRoleId(roleId);
    return userRole;
  }
}