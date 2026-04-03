package com.seletoai.adapters.persistence.analytics;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.analytics.Insight;

import java.util.List;

public interface SpringInsightRepository extends BaseRepository<Insight> {

  List<Insight> findByProcessId(Integer processId);
}
