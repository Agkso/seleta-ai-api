package com.seletoai.adapters.controller.roles;

import com.seletoai.core.domain.role.Role;
import com.seletoai.core.useCase.role.RolesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RolesUseCase getRolesUseCase;

  @GetMapping
  public List<Role> listAll() {
    return getRolesUseCase.execute();
  }
}