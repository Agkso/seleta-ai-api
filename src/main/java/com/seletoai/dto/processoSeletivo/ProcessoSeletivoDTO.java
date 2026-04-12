package com.seletoai.dto.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.TipoProcesso;
import lombok.Builder;

import java.time.LocalDateTime;

public final class ProcessoSeletivoDTO {

  private ProcessoSeletivoDTO() {}

  @Builder
  public record ProcessoSeletivoRequest(
    Long instituicaoId,
    String titulo,
    String numeroEdital,
    LocalDateTime dataInicioInscricao,
    LocalDateTime dataFimInscricao,
    TipoProcesso tipoProcesso
  ) {}

  @Builder
  public record AdicionarCargoRequest(
    String titulo
  ) {}
}
