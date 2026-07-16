package com.seletoai.adapters.controller.user;

import com.seletoai.adapters.security.AuthenticatedUser;
import com.seletoai.core.domain.role.Role;
import com.seletoai.core.domain.user.User;
import com.seletoai.core.ports.in.user.CreateUserUseCasePort;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import com.seletoai.core.ports.out.user.UserRepositoryPort;
import com.seletoai.core.ports.out.userRole.UserRoleRepositoryPort;
import com.seletoai.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final CreateUserUseCasePort createUserUseCase;
  private final UserRepositoryPort userRepository;
  private final UserRoleRepositoryPort userRoleRepository;
  private final RoleRepositoryPort roleRepository;

  @GetMapping("/me")
  public ResponseEntity<UserDTO.MeResponse> me(@AuthenticationPrincipal AuthenticatedUser principal) {
    User user = userRepository.findById(principal.getUserId())
      .orElseThrow();

    List<String> roles = userRoleRepository.findByUserId(principal.getUserId()).stream()
      .map(ur -> roleRepository.findById(ur.getRoleId()))
      .flatMap(java.util.Optional::stream)
      .map(Role::getName)
      .toList();

    Long instituicaoId = user.getInstituicao() != null ? user.getInstituicao().getId() : null;

    return ResponseEntity.ok(new UserDTO.MeResponse(
      user.getId(),
      user.getName(),
      user.getEmail(),
      roles,
      instituicaoId
    ));
  }
}
