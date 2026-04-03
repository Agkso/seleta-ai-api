package com.seletoai.adapters.persistence.analytics;

import com.seletoai.core.domain.analytics.AnalyticsSnapshot;
import com.seletoai.core.ports.out.analytics.AnalyticsSnapshotRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnalyticsSnapshotRepositoryAdapter implements AnalyticsSnapshotRepositoryPort {

  private final SpringAnalyticsSnapshotRepository repository;

  @Override
  public List<AnalyticsSnapshot> findByProcessIdOrderByDateAsc(Integer processId) {
    return repository.findByProcessIdOrderByDateAsc(processId);
  }
}
