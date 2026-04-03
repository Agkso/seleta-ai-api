package com.seletoai.dto.processoSeletivo;

import lombok.Builder;

import java.time.LocalDateTime;

public final class ProcessoSeletivoDTO {

  private ProcessoSeletivoDTO() {}

  @Builder
  public record ProcessoSeletivoRequest(
    Integer instituicaoId,
    String titulo,
    String numeroEdital,
    LocalDateTime dataInicioInscricao,
    LocalDateTime dataFimInscricao
  ) {}
}
