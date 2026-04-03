package com.seletoai.core.mapper.analytics;

import com.seletoai.core.domain.analytics.Alert;
import com.seletoai.core.domain.analytics.AnalyticsSnapshot;
import com.seletoai.core.domain.analytics.Insight;
import com.seletoai.dto.analytics.AnalyticsDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalyticsMapper {

  public List<AnalyticsDTO.SerieDTO> toSerieList(List<AnalyticsSnapshot> snapshots) {
    return snapshots.stream().map(this::toSerie).toList();
  }

  public AnalyticsDTO.SerieDTO toSerie(AnalyticsSnapshot snapshot) {
    return AnalyticsDTO.SerieDTO.builder()
      .date(snapshot.getDate() != null ? snapshot.getDate().toString() : null)
      .inscricoes(snapshot.getTotalInscricoes())
      .aprovados(snapshot.getTotalAprovados())
      .build();
  }

  public List<AnalyticsDTO.InsightDTO> toInsightDtoList(List<Insight> insights) {
    return insights.stream().map(this::toInsightDto).toList();
  }

  public AnalyticsDTO.InsightDTO toInsightDto(Insight insight) {
    return AnalyticsDTO.InsightDTO.builder()
      .type(insight.getType())
      .title(insight.getTitle())
      .description(insight.getDescription())
      .build();
  }

  public List<AnalyticsDTO.AlertDTO> toAlertDtoList(List<Alert> alerts) {
    return alerts.stream().map(this::toAlertDto).toList();
  }

  public AnalyticsDTO.AlertDTO toAlertDto(Alert alert) {
    return AnalyticsDTO.AlertDTO.builder()
      .type(alert.getType())
      .message(alert.getMessage())
      .build();
  }
}
