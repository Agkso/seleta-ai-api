package com.seletoai.adapters.security;

import com.seletoai.core.domain.user.User;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import com.seletoai.core.ports.out.user.UserRepositoryPort;
import com.seletoai.core.ports.out.userRole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepositoryPort userRepository;
  private final UserRoleRepositoryPort userRoleRepository;
  private final RoleRepositoryPort roleRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmailAndDeletedAtIsNull(email)
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

    List<GrantedAuthority> authorities = userRoleRepository.findByUserId(user.getId()).stream()
      .map(userRole -> roleRepository.findById(userRole.getRoleId()))
      .flatMap(java.util.Optional::stream)
      .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
      .collect(java.util.stream.Collectors.toList());

    Long instituicaoId = user.getInstituicao() != null ? user.getInstituicao().getId() : null;

    return new AuthenticatedUser(
      user.getEmail(),
      user.getPassword(),
      authorities,
      user.getId(),
      instituicaoId
    );
  }
}
