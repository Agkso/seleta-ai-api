package com.seletoai.dto.error;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

public final class ErroDTO {

  private ErroDTO() {}

  @Builder
  public record ErroResponse(
    int status,
    String erro,
    String mensagem,
    Instant timestamp,
    String path,
    List<CampoErro> detalhes
  ) {}

  public record CampoErro(String campo, String mensagem) {}
}
