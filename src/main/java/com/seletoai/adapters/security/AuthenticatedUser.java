package com.seletoai.adapters.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticatedUser extends User {

  private final Long userId;
  private final Long instituicaoId;

  public AuthenticatedUser(
    String email,
    String password,
    Collection<? extends GrantedAuthority> authorities,
    Long userId,
    Long instituicaoId
  ) {
    super(email, password, authorities);
    this.userId = userId;
    this.instituicaoId = instituicaoId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getInstituicaoId() {
    return instituicaoId;
  }

  public boolean isAdmin() {
    return getAuthorities().stream()
      .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
  }
}
