package com.seletoai.core.useCase.onboarding;

import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.domain.role.Role;
import com.seletoai.core.domain.role.RoleNomes;
import com.seletoai.core.ports.in.onboarding.OnboardingUseCasePort;
import com.seletoai.core.ports.in.instituicao.CriarInstituicaoUseCasePort;
import com.seletoai.core.ports.in.user.CreateUserUseCasePort;
import com.seletoai.core.ports.out.role.RoleRepositoryPort;
import com.seletoai.dto.auth.AuthDTO;
import com.seletoai.dto.instituicao.InstituicaoDTO;
import com.seletoai.dto.onboarding.OnboardingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnboardingUseCase implements OnboardingUseCasePort {

  private final CriarInstituicaoUseCasePort criarInstituicaoUseCase;
  private final CreateUserUseCasePort createUserUseCase;
  private final RoleRepositoryPort roleRepository;

  @Override
  @Transactional
  public AuthDTO.AuthResponse execute(OnboardingDTO.OnboardingRequest request) {
    if (request == null
      || request.nomeResponsavel() == null || request.nomeResponsavel().isBlank()
      || request.emailResponsavel() == null || request.emailResponsavel().isBlank()
      || request.senhaResponsavel() == null || request.senhaResponsavel().isBlank()) {
      throw new RegraNegocioException("Dados do responsável pela instituição são obrigatórios.");
    }

    Instituicao instituicao = criarInstituicaoUseCase.execute(
      InstituicaoDTO.InstituicaoRequest.builder()
        .cnpj(request.cnpj())
        .razaoSocial(request.razaoSocial())
        .nomeFantasia(request.nomeFantasia())
        .build()
    );

    Role roleContratante = roleRepository.findByName(RoleNomes.CONTRATANTE)
      .orElseThrow(() -> new RegraNegocioException("Role CONTRATANTE não configurada."));

    AuthDTO.RegisterRequest registerRequest = AuthDTO.RegisterRequest.builder()
      .name(request.nomeResponsavel())
      .email(request.emailResponsavel())
      .password(request.senhaResponsavel())
      .roleId(roleContratante.getId())
      .instituicaoId(instituicao.getId())
      .build();

    return createUserUseCase.execute(registerRequest);
  }
}
