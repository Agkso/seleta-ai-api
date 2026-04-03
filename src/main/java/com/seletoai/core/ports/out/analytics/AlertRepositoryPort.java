package com.seletoai.core.ports.out.analytics;

import com.seletoai.core.domain.analytics.Alert;

import java.util.List;

public interface AlertRepositoryPort {

  List<Alert> findByProcessIdAndResolvedIsFalse(Long processId);
}
