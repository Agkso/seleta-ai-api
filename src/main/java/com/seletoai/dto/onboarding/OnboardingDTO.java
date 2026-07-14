package com.seletoai.dto.onboarding;

import lombok.Builder;

public final class OnboardingDTO {

  private OnboardingDTO() {}

  @Builder
  public record OnboardingRequest(
    String cnpj,
    String razaoSocial,
    String nomeFantasia,
    String nomeResponsavel,
    String emailResponsavel,
    String senhaResponsavel
  ) {}
}
