package com.seletoai.core.ports.in.analytics;

import com.seletoai.dto.analytics.AnalyticsDTO;

public interface GetDashboardAnalyticsUseCasePort {

  AnalyticsDTO.DashboardAnalyticsResponse execute(Integer processId);
}
