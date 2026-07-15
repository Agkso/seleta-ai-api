---
name: status
description: Business rules, internal patterns, and communication contracts for the status
  catalog module. Load before changing domain statuses or seeds.
---

# Module: status

**Path:** `core/domain/status`, `adapters/persistence/status`, ports/out/status

## Responsibility

Shared status catalog used by processes, candidates, and potentially other aggregates. Statuses
are typed (`TipoStatus` / tipo string) and coded (e.g. `RASCUNHO`, `INSCRITO`).

## Business Rules

- Lookup pattern: `findByCodigoAndTipo(codigo, tipo)`.
- Process domain uses tipo `PROCESSO` (`ProcessoStatusCodes.TIPO_DOMINIO_PROCESSO`).
- Candidate initial status uses codigo `INSCRITO`, tipo `CANDIDATE`.
- Seeds live in Flyway (`V12`–`V16` and related); code constants must match seeds.

## Internal Patterns

**Structure:**
```
core/domain/status/Status.java
core/domain/status/TipoStatus.java
core/ports/out/status/StatusRepositoryPort.java
adapters/persistence/status/
  SpringStatusRepository.java
  StatusRepositoryAdapter.java
```

**Naming:** `Status` entity; consumers hold constants in their own domain (`ProcessoStatusCodes`).

**Key abstractions:**
- Catalog entity + repository port only (no dedicated use case/controller)

## Relationships

### Emits

- Status entities to other modules

### Consumes

- Flyway seeds

### Depends On

- Used by **processoSeletivo**, **candidate**, and any future status-driven flows

## Known Gotchas

- Missing seed rows cause runtime failures on create/publish — always ship migration + constant together.
- Do not hardcode status **ids**; always resolve by codigo+tipo.
)
