package com.seletoai.dto.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class CandidateDTO {

  private CandidateDTO() {}

  public record InscricaoRequest(
    @NotNull(message = "Cargo é obrigatório.")
    Long cargoId,

    @NotBlank(message = "CPF é obrigatório.")
    String cpf
  ) {}

  public record CandidateResponse(
    Long id,
    String nome,
    String cpf,
    String email,
    String statusCodigo,
    String statusNome,
    Long cargoId,
    Long processoId
  ) {
    public static CandidateResponse from(Candidate c) {
      return new CandidateResponse(
        c.getId(),
        c.getNome(),
        c.getCpf(),
        c.getEmail(),
        c.getStatus() != null ? c.getStatus().getCodigo() : null,
        c.getStatus() != null ? c.getStatus().getNome() : null,
        c.getCargoId(),
        c.getProcessoId()
      );
    }
  }

  public record AtualizarStatusRequest(
    @NotBlank(message = "Código do status é obrigatório.")
    String statusCodigo
  ) {}
}
