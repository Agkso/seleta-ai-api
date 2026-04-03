package com.seletoai.adapters.persistence.analytics;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.analytics.Alert;

import java.util.List;

public interface SpringAlertRepository extends BaseRepository<Alert> {

  List<Alert> findByProcessIdAndResolvedIsFalse(Integer processId);
}
