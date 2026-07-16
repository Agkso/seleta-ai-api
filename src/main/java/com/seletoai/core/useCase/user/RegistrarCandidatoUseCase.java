package com.seletoai.core.useCase.user;

import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.role.RoleNomes;
import com.seletoai.core.ports.in.user.CreateUserUseCasePort;
import com.seletoai.core.ports.in.user.RegistrarCandidatoUseCasePort;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import com.seletoai.dto.auth.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrarCandidatoUseCase implements RegistrarCandidatoUseCasePort {

  private final CreateUserUseCasePort createUserUseCase;
  private final RoleRepositoryPort roleRepository;

  @Override
  @Transactional
  public AuthDTO.AuthResponse execute(AuthDTO.CandidatoRegisterRequest request) {
    var role = roleRepository.findByName(RoleNomes.CANDIDATO)
      .orElseThrow(() -> new RegraNegocioException("Role CANDIDATO não configurada."));

    return createUserUseCase.execute(
      AuthDTO.RegisterRequest.builder()
        .name(request.name())
        .email(request.email())
        .password(request.password())
        .roleId(role.getId())
        .build()
    );
  }
}
