---
name: instituicao
description: Business rules, internal patterns, and communication contracts for the instituicao
  module. Load before working on any feature in institutions / tenants / CNPJ.
---

# Module: instituicao

**Path:** `core/domain/instituicao`, `core/useCase/instituicao`, `adapters/.../instituicao`

## Responsibility

Manage tenant organizations (instituições): create, read, update, delete, list. Each institution
is the multi-tenant boundary for processos seletivos and CONTRATANTE users.

## Business Rules

- CNPJ is unique and validated/normalized via `dto/validation` (`CNPJ`, `CnpjNormalizador`,
  `CNPJValidator`).
- Required fields: `cnpj`, `razaoSocial`, `nomeFantasia`.
- Onboarding creates an institution then a CONTRATANTE user bound to it.
- Tenant rule: non-admin users operate only within their `instituicao_id` (enforced in consumers
  via `AuthContext`; keep institution APIs consistent with that policy).

## Internal Patterns

**Structure:**
```
core/domain/instituicao/Instituicao.java
core/ports/in/instituicao/
  CriarInstituicaoUseCasePort.java
  AtualizarInstituicaoUseCasePort.java
  BuscarInstituicaoUseCasePort.java
  ExcluirInstituicaoUseCasePort.java
  ListarInstituicoesUseCasePort.java
core/ports/out/instituicao/InstituicaoRepositoryPort.java
core/useCase/instituicao/
  Criar|Atualizar|Buscar|Excluir|ListarInstituicoesUseCase.java
core/mapper/instituicao/InstituicaoMapper.java
adapters/controller/instituicao/InstituicaoController.java
adapters/persistence/instituicao/...
dto/instituicao/InstituicaoDTO.java
dto/validation/CNPJ*.java
```

**Naming:** Portuguese verbs (`Criar`, `Listar`, `Buscar`, `Atualizar`, `Excluir`).

**Key abstractions:**
- `Instituicao` entity (tenant root)
- Port-per-use-case style (one interface per action)

## Relationships

### Emits

- New/updated `Instituicao` entities for onboarding and CRUD

### Consumes

- CNPJ validators from `dto/validation`

### Depends On

- Used by **onboarding**, **user** (optional FK), **processoSeletivo** (required FK)

## Known Gotchas

- Deleting an institution may break FK-linked users/processes — check integrity before hard delete;
  prefer soft-delete if product requires.
- CNPJ must be normalized the same way at write and unique constraint.
)
