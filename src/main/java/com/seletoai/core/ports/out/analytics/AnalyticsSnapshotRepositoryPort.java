package com.seletoai.core.ports.out.analytics;

import com.seletoai.core.domain.analytics.AnalyticsSnapshot;

import java.util.List;

public interface AnalyticsSnapshotRepositoryPort {

  List<AnalyticsSnapshot> findByProcessIdOrderByDateAsc(Integer processId);
}
