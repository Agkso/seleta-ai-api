package com.seletoai.adapters.controller.analytics;

import com.seletoai.core.ports.in.analytics.GetDashboardAnalyticsUseCasePort;
import com.seletoai.dto.analytics.AnalyticsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/processos/{processId}/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

  private final GetDashboardAnalyticsUseCasePort getDashboardAnalyticsUseCase;

  @GetMapping("/dashboard")
  public ResponseEntity<AnalyticsDTO.DashboardAnalyticsResponse> dashboard(
    @PathVariable Integer processId
  ) {
    return ResponseEntity.ok(getDashboardAnalyticsUseCase.execute(processId));
  }
}
