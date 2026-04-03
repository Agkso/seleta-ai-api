package com.seletoai.core.ports.in.role;

import com.seletoai.core.domain.role.Role;

import java.util.List;

public interface RolesUseCasePort {

  List<Role> execute();
}
