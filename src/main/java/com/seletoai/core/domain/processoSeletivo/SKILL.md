---
name: processoSeletivo
description: Business rules, internal patterns, and communication contracts for the processoSeletivo
  module. Load before working on any feature in selection processes / cargos / audit.
---

# Module: processoSeletivo

**Path:** `core/domain/processoSeletivo`, `core/useCase/processoSeletivo`, `adapters/.../processoSeletivo`

## Responsibility

Own the selection process aggregate: create processes for an institution, manage cargos while
draft, run lifecycle transitions, expose public listings, and write audit records.

Also load **lifecycle** skill for transition rules:
`src/main/java/com/seletoai/core/domain/processoSeletivo/lifecycle/SKILL.md`.

## Business Rules

- Every process belongs to an `Instituicao` (required FK).
- Created in status `RASCUNHO` (`ProcessoStatusCodes` + **status** catalog type `PROCESSO`).
- Types: `TipoProcesso` = `PUBLICO` | `UNIVERSIDADE` | `EMPRESA`.
- Create and lifecycle mutations require `AuthContext.garantirAcessoInstituicao(...)`.
- Controller mutations: `@PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")`.
- Public list: processes with status `PUBLICADO` (paginated).
- Cargos only while `RASCUNHO`; publish requires ≥1 cargo; PUBLICO/UNIVERSIDADE require edital number.
- Audit events use string constants in `ProcessoEventos` (not a message bus).

## Internal Patterns

**Structure:**
```
core/domain/processoSeletivo/
  ProcessoSeletivo.java
  ProcessoCargo.java
  ProcessoAuditoria.java
  ProcessoLifecycleRules.java
  ProcessoStatusCodes.java
  ProcessoEventos.java
  TipoProcesso.java
core/ports/in|out/processoSeletivo/...
core/useCase/processoSeletivo/
  ProcessoSeletivoUseCase.java
  AdicionarCargoAoProcessoUseCase.java
  Publicar|Iniciar|Encerrar|CancelarProcessoUseCase.java
  ProcessoAuditoriaWriter.java
adapters/controller/processoSeletivo/ProcessoSeletivoController.java  # /api/processos
adapters/persistence/processoSeletivo/...
dto/processoSeletivo/ProcessoSeletivoDTO.java
```

**Naming:** PT verbs for lifecycle; REST base `/api/processos`.

**Key abstractions:**
- `ProcessoLifecycleRules` — pure static guards
- `ProcessoStatusCodes` / `ProcessoEventos` — constants
- `ProcessoAuditoriaWriter` — audit side-effect helper
- Status resolved via `StatusRepositoryPort.findByCodigoAndTipo`

## Relationships

### Emits

- Process/cargo/audit persistence
- Audit event labels for lifecycle (see lifecycle skill)
- Public process pages for candidates

### Consumes

- **status** catalog for process statuses
- **auth** `AuthContext` for tenancy
- Called by **candidate** for inscription eligibility

### Depends On

- **instituicao**, **status**, **auth**/security roles
- Submodule: **lifecycle**

## Known Gotchas

- `numeroEdital` is `@Column(nullable = false, unique = true)` on the entity even though lifecycle
  rules talk about blank edital for some types — align DTO validation and DB constraints carefully.
- `ProcessoSeletivoUseCase` is annotated `@Component` while other use cases use `@Service` — both work; prefer `@Service` for new ones.
- Tenant checks must run **after** load-by-id and use the process’s institution, not a client-supplied id alone.
- Never change status with raw strings; use `ProcessoStatusCodes` + status repository.
)
