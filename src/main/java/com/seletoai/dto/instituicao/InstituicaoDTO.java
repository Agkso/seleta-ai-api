package com.seletoai.dto.instituicao;

import com.seletoai.dto.validation.CNPJ;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public final class InstituicaoDTO {

  private InstituicaoDTO() {}

  @Builder
  public record InstituicaoRequest(
    @NotBlank(message = "CNPJ é obrigatório.")
    @CNPJ
    String cnpj,

    @NotBlank(message = "Razão social é obrigatória.")
    @Size(max = 255)
    String razaoSocial,

    @NotBlank(message = "Nome fantasia é obrigatório.")
    @Size(max = 255)
    String nomeFantasia
  ) {}
}
