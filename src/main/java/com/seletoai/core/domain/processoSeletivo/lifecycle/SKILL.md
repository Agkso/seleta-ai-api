---
name: processoSeletivo-lifecycle
description: Business rules for processo seletivo lifecycle transitions (publish, start, end,
  cancel, cargos). Load with ../SKILL.md (processoSeletivo) when changing status flows.
---

# Module: processoSeletivo / lifecycle

**Path:** `core/domain/processoSeletivo/ProcessoLifecycleRules.java` + lifecycle use cases

## Responsibility

Encode and execute legal status transitions and preconditions for a selection process.

## Business Rules

| Action | From status | Extra preconditions |
|--------|-------------|---------------------|
| Add cargo | `RASCUNHO` | — |
| Publish | `RASCUNHO` | ≥1 cargo; edital required if type PUBLICO or UNIVERSIDADE |
| Start (inscrições) | `PUBLICADO` | — |
| End | `EM_ANDAMENTO` | — |
| Cancel | any except `ENCERRADO` / `CANCELADO` | — |
| Accept inscription | `PUBLICADO` or `EM_ANDAMENTO` | now ∈ [dataInicio, dataFim] |

- Failures throw `RegraNegocioException` with Portuguese messages.
- Each successful transition writes audit via `ProcessoAuditoriaWriter` + `ProcessoEventos.*`.
- Always re-check tenancy: `authContext.garantirAcessoInstituicao(processo.getInstituicao().getId())`.

## Internal Patterns

**Structure:**
```
core/domain/processoSeletivo/ProcessoLifecycleRules.java
core/useCase/processoSeletivo/
  AdicionarCargoAoProcessoUseCase.java
  PublicarProcessoUseCase.java
  IniciarProcessoUseCase.java
  EncerrarProcessoUseCase.java
  CancelarProcessoUseCase.java
  ProcessoAuditoriaWriter.java
```

**Naming:** one use case class per transition; pure rules stay in `ProcessoLifecycleRules`.

**Key abstractions:**
- Static pure functions in `ProcessoLifecycleRules` (unit-test without Spring)
- Status codes: `RASCUNHO`, `PUBLICADO`, `EM_ANDAMENTO`, `ENCERRADO`, `CANCELADO`
- Events: `PROCESSO_PUBLICADO`, `PROCESSO_INICIADO`, `PROCESSO_ENCERRADO`, `PROCESSO_CANCELADO`

## Relationships

### Emits

- Updated `ProcessoSeletivo.status`
- `ProcessoAuditoria` rows

### Consumes

- Cargo counts from `ProcessoCargoRepositoryPort`
- Status entities from **status**

### Depends On

- Parent **processoSeletivo**, **status**, **auth** context

## Known Gotchas

- Keep rules in the pure class; use cases only orchestrate load → guard → set status → save → audit.
- Inscription window check is used by **candidate**, not only lifecycle use cases — do not duplicate logic.
- Adding a new status requires Flyway seed + `ProcessoStatusCodes` + rules + tests.
)
