package com.seletoai.dto.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.TipoProcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

public final class ProcessoSeletivoDTO {

  private ProcessoSeletivoDTO() {}

  @Builder
  public record ProcessoSeletivoRequest(
    @NotNull(message = "Instituição é obrigatória.")
    Long instituicaoId,

    @NotBlank(message = "Título é obrigatório.")
    String titulo,

    @NotBlank(message = "Número do edital é obrigatório.")
    String numeroEdital,

    @NotNull(message = "Data de início da inscrição é obrigatória.")
    LocalDateTime dataInicioInscricao,

    @NotNull(message = "Data de fim da inscrição é obrigatória.")
    LocalDateTime dataFimInscricao,

    TipoProcesso tipoProcesso
  ) {}

  @Builder
  public record AdicionarCargoRequest(
    @NotBlank(message = "Título do cargo é obrigatório.")
    String titulo
  ) {}
}
