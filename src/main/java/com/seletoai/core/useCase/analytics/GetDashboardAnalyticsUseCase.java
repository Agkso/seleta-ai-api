package com.seletoai.core.useCase.analytics;

import com.seletoai.core.domain.analytics.Alert;
import com.seletoai.core.domain.analytics.AnalyticsSnapshot;
import com.seletoai.core.domain.analytics.Insight;
import com.seletoai.core.mapper.analytics.AnalyticsMapper;
import com.seletoai.core.ports.in.analytics.GetDashboardAnalyticsUseCasePort;
import com.seletoai.core.ports.out.analytics.AlertRepositoryPort;
import com.seletoai.core.ports.out.analytics.AnalyticsSnapshotRepositoryPort;
import com.seletoai.core.ports.out.analytics.InsightRepositoryPort;
import com.seletoai.dto.analytics.AnalyticsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDashboardAnalyticsUseCase implements GetDashboardAnalyticsUseCasePort {

  private final AnalyticsSnapshotRepositoryPort snapshotRepository;
  private final InsightRepositoryPort insightRepository;
  private final AlertRepositoryPort alertRepository;
  private final AnalyticsMapper mapper;

  @Override
  public AnalyticsDTO.DashboardAnalyticsResponse execute(Long processId) {
    List<AnalyticsSnapshot> snapshots = snapshotRepository.findByProcessIdOrderByDateAsc(processId);
    List<Insight> insights = insightRepository.findByProcessId(processId);
    List<Alert> alerts = alertRepository.findByProcessIdAndResolvedIsFalse(processId);

    AnalyticsSnapshot latest = snapshots.isEmpty() ? null : snapshots.get(snapshots.size() - 1);

    Integer totalInscricoes = latest != null ? latest.getTotalInscricoes() : 0;
    Integer totalAprovado = latest != null ? latest.getTotalAprovados() : 0;
    double taxaAprovacao = calculateTaxa(latest);

    return AnalyticsDTO.DashboardAnalyticsResponse.builder()
      .totalInscricoes(zeroIfNull(totalInscricoes))
      .totalAprovado(zeroIfNull(totalAprovado))
      .taxaAprovacao(taxaAprovacao)
      .serie(mapper.toSerieList(snapshots))
      .insights(mapper.toInsightDtoList(insights))
      .alerts(mapper.toAlertDtoList(alerts))
      .build();
  }

  private static double calculateTaxa(AnalyticsSnapshot latest) {
    if (latest == null) {
      return 0.0;
    }
    int aprov = zeroIfNull(latest.getTotalAprovados());
    int reprov = zeroIfNull(latest.getTotalReprovados());
    int denom = aprov + reprov;
    if (denom == 0) {
      return 0.0;
    }
    return (double) aprov / (double) denom * 100.0;
  }

  private static int zeroIfNull(Integer v) {
    return v == null ? 0 : v;
  }
}
