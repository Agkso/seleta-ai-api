package com.seletoai.adapters.persistence.analytics;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.analytics.AnalyticsSnapshot;

import java.util.List;

public interface SpringAnalyticsSnapshotRepository extends BaseRepository<AnalyticsSnapshot> {

  List<AnalyticsSnapshot> findByProcessIdOrderByDateAsc(Integer processId);
}
