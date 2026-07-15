---
name: analytics
description: Business rules, internal patterns, and communication contracts for the analytics
  module. Load before working on any feature in dashboards / insights / alerts.
---

# Module: analytics

**Path:** `core/domain/analytics`, `core/useCase/analytics`, `adapters/.../analytics`

## Responsibility

Read-side dashboard for a process: time series snapshots, insights, and unresolved alerts;
compute summary metrics (inscriptions, approvals, approval rate).

## Business Rules

- Scoped by `processId` (processo seletivo id).
- Snapshot series ordered by date ascending; latest snapshot drives totals.
- Approval rate = `totalAprovados / (totalAprovados + totalReprovados) * 100` (0 if denom 0).
- Alerts: only unresolved (`resolved == false`).
- Currently read-only aggregation — writers of snapshots/insights/alerts may be external/future AI jobs.

## Internal Patterns

**Structure:**
```
core/domain/analytics/
  AnalyticsSnapshot.java
  Insight.java
  Alert.java
core/ports/in/analytics/GetDashboardAnalyticsUseCasePort.java
core/ports/out/analytics/
  AnalyticsSnapshotRepositoryPort.java
  InsightRepositoryPort.java
  AlertRepositoryPort.java
core/useCase/analytics/GetDashboardAnalyticsUseCase.java
core/mapper/analytics/AnalyticsMapper.java
adapters/controller/analytics/AnalyticsController.java
adapters/persistence/analytics/...
dto/analytics/AnalyticsDTO.java
```

**Naming:** EN dashboard vocabulary (`Insight`, `Alert`, `Snapshot`).

**Key abstractions:**
- Three read ports composed in one use case
- Mapper builds DTO lists for series/insights/alerts

## Relationships

### Emits

- `AnalyticsDTO.DashboardAnalyticsResponse` for API clients

### Consumes

- Persisted snapshots/insights/alerts (no direct write path in core today)

### Depends On

- Conceptually **processoSeletivo** (processId); no hard package dependency required for reads

## Known Gotchas

- No automatic computation of snapshots from candidates yet — empty dashboard is valid.
- Tenant: ensure controller/use case verifies caller can access the process’s institution when securing endpoints.
- Future AI modules should write via ports, not controller→repository shortcuts.
)
