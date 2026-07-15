package com.seletoai.dto.onboarding;

import com.seletoai.dto.validation.CNPJ;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public final class OnboardingDTO {

  private OnboardingDTO() {}

  @Builder
  public record OnboardingRequest(
    @NotBlank(message = "CNPJ é obrigatório.")
    @CNPJ
    String cnpj,

    @NotBlank(message = "Razão social é obrigatória.")
    @Size(max = 255)
    String razaoSocial,

    @NotBlank(message = "Nome fantasia é obrigatório.")
    @Size(max = 255)
    String nomeFantasia,

    @NotBlank(message = "Nome do responsável é obrigatório.")
    String nomeResponsavel,

    @NotBlank(message = "Email do responsável é obrigatório.")
    @Email(message = "Email inválido.")
    String emailResponsavel,

    @NotBlank(message = "Senha do responsável é obrigatória.")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres.")
    String senhaResponsavel
  ) {}
}
