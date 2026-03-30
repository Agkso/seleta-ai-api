package com.seletoai.dto.processoSeletivo;

  import java.time.LocalDateTime;
public class ProcessoSeletivoRecord {


  public record ProcessoSeletivoRequest(
    Integer instituicaoId,
    String titulo,
    String numeroEdital,
    LocalDateTime dataInicioInscricao,
    LocalDateTime dataFimInscricao
  ) {}
}
