package com.seletoai.adapters.persistence.userRole;

import com.seletoai.core.domain.userRole.UserRole;
import com.seletoai.core.ports.out.userRole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRoleRepositoryAdapter implements UserRoleRepositoryPort {

  private final SpringUserRoleRepository repository;

  @Override
  public UserRole save(UserRole userRole) {
    return repository.save(userRole);
  }

  @Override
  public List<UserRole> findByUserId(Long userId) {
    return repository.findByUserId(userId);
  }

  @Override
  public List<UserRole> findByRoleId(Integer roleId){
    return repository.findByRoleId(roleId);
  }
}