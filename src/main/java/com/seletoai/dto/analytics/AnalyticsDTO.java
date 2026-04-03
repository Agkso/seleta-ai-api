package com.seletoai.dto.analytics;

import lombok.Builder;

import java.util.List;

public final class AnalyticsDTO {

  private AnalyticsDTO() {}

  @Builder
  public record DashboardAnalyticsResponse(
    Integer totalInscricoes,
    Integer totalAprovado,
    Double taxaAprovacao,
    List<SerieDTO> serie,
    List<InsightDTO> insights,
    List<AlertDTO> alerts
  ) {}

  @Builder
  public record SerieDTO(
    String date,
    Integer inscricoes,
    Integer aprovados
  ) {}

  @Builder
  public record InsightDTO(
    String type,
    String title,
    String description
  ) {}

  @Builder
  public record AlertDTO(
    String type,
    String message
  ) {}
}
