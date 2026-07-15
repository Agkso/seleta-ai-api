---
name: candidate
description: Business rules, internal patterns, and communication contracts for the candidate
  module. Load before working on any feature in candidate inscriptions.
---

# Module: candidate

**Path:** `core/domain/candidate`, `core/useCase/candidate`, `adapters/.../candidate`

## Responsibility

Register candidates (inscrições) against a processo seletivo and cargo, setting initial candidate
status.

## Business Rules

- `processoId` and `cargoId` are mandatory.
- Process must accept inscriptions (`ProcessoLifecycleRules.garantirProcessoAceitaInscricao`):
  status PUBLICADO or EM_ANDAMENTO and within inscription dates.
- Cargo must belong to the process (`existsByIdAndProcesso_Id`).
- Initial status: code `INSCRITO`, type `CANDIDATE` from **status** catalog.
- Candidate holds `processoId` / `cargoId` as plain Long FKs (not JPA associations to process).

## Internal Patterns

**Structure:**
```
core/domain/candidate/Candidate.java
core/domain/candidateStatus/CandidateStatus.java   # if used alongside Status
core/ports/in/candidate/CreateCandidateUseCasePort.java
core/ports/out/candidate/
  CandidateRepositoryPort.java
  CandidateStatusRepositoryPort.java
core/useCase/candidate/CreateCandidateUseCase.java
adapters/controller/candidate/CandidateController.java
adapters/persistence/candidate/...
```

**Naming:** EN (`CreateCandidate`, entity `Candidate`, table `candidatos`).

**Key abstractions:**
- Reuses **processoSeletivo** lifecycle rules for eligibility
- Status via shared **status** module

## Relationships

### Emits

- New `Candidate` rows with status INSCRITO

### Consumes

- Process + cargo validation from **processoSeletivo**
- Status lookup from **status**

### Depends On

- **processoSeletivo**, **status**

## Known Gotchas

- No full JPA relation to `ProcessoSeletivo` — keep IDs consistent manually.
- Analytics totals depend on candidate/status data consistency; changing status codes needs seed alignment.
- Multi-tenant: listing candidates for a process should ensure the caller can access that process’s institution (enforce if missing).
)
