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
    String titulo,

    @NotNull(message = "Número de vagas é obrigatório.")
    @jakarta.validation.constraints.Min(value = 1, message = "Mínimo de 1 vaga.")
    Integer vagas
  ) {}

  public record ProcessoSeletivoResponse(
    Long id,
    String titulo,
    String numeroEdital,
    java.time.LocalDateTime dataInicioInscricao,
    java.time.LocalDateTime dataFimInscricao,
    com.seletoai.core.domain.processoSeletivo.TipoProcesso tipoProcesso,
    String statusCodigo,
    String statusNome,
    Long instituicaoId,
    String instituicaoNome
  ) {
    public static ProcessoSeletivoResponse from(com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo p) {
      return new ProcessoSeletivoResponse(
        p.getId(),
        p.getTitulo(),
        p.getNumeroEdital(),
        p.getDataInicioInscricao(),
        p.getDataFimInscricao(),
        p.getTipoProcesso(),
        p.getStatus() != null ? p.getStatus().getCodigo() : null,
        p.getStatus() != null ? p.getStatus().getNome() : null,
        p.getInstituicao() != null ? p.getInstituicao().getId() : null,
        p.getInstituicao() != null ? p.getInstituicao().getRazaoSocial() : null
      );
    }
  }

  public record CargoResponse(
    Long id,
    String titulo,
    int vagas
  ) {
    public static CargoResponse from(com.seletoai.core.domain.processoSeletivo.ProcessoCargo c) {
      return new CargoResponse(c.getId(), c.getTitulo(), c.getVagas());
    }
  }
}
