package com.seletoai.core.ports.out.analytics;

import com.seletoai.core.domain.analytics.Insight;

import java.util.List;

public interface InsightRepositoryPort {

  List<Insight> findByProcessId(Long processId);
}
