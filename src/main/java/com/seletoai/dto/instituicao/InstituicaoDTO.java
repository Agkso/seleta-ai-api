package com.seletoai.dto.instituicao;

import lombok.Builder;

public final class InstituicaoDTO {

  private InstituicaoDTO() {}

  @Builder
  public record InstituicaoRequest(
    String cnpj,
    String razaoSocial,
    String nomeFantasia
  ) {}
}
