package com.seletoai.adapters.persistence.analytics;

import com.seletoai.core.domain.analytics.Alert;
import com.seletoai.core.ports.out.analytics.AlertRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertRepositoryAdapter implements AlertRepositoryPort {

  private final SpringAlertRepository repository;

  @Override
  public List<Alert> findByProcessIdAndResolvedIsFalse(Long processId) {
    return repository.findByProcessIdAndResolvedIsFalse(processId);
  }
}
