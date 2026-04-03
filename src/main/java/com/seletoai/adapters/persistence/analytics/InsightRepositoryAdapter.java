package com.seletoai.adapters.persistence.analytics;

import com.seletoai.core.domain.analytics.Insight;
import com.seletoai.core.ports.out.analytics.InsightRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InsightRepositoryAdapter implements InsightRepositoryPort {

  private final SpringInsightRepository repository;

  @Override
  public List<Insight> findByProcessId(Long processId) {
    return repository.findByProcessId(processId);
  }
}
