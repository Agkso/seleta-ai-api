---
name: onboarding
description: Business rules, internal patterns, and communication contracts for the onboarding
  module. Load before working on any feature in institution+user bootstrap signup.
---

# Module: onboarding

**Path:** `core/useCase/onboarding`, `adapters/controller/onboarding`, `dto/onboarding`

## Responsibility

Single transactional flow to register a new institution and its responsible CONTRATANTE user,
returning an auth session.

## Business Rules

- Request must include responsible person: `nomeResponsavel`, `emailResponsavel`, `senhaResponsavel`.
- Institution fields: CNPJ, razão social, nome fantasia (via `CriarInstituicaoUseCasePort`).
- User is always created with role **CONTRATANTE** (looked up by name; must exist in seeds).
- `instituicaoId` is set on the user from the institution just created.
- Entire flow is `@Transactional` — failure rolls back both institution and user.

## Internal Patterns

**Structure:**
```
core/ports/in/onboarding/OnboardingUseCasePort.java
core/useCase/onboarding/OnboardingUseCase.java
adapters/controller/onboarding/OnboardingController.java
dto/onboarding/OnboardingDTO.java
```

**Naming:** composition root style — no own persistence; orchestrates ports.

**Key abstractions:**
- Orchestrates `CriarInstituicaoUseCasePort` + `CreateUserUseCasePort` + `RoleRepositoryPort`

## Relationships

### Emits

- Calls that create **instituicao** + **user** + tokens

### Consumes

- Institution create port
- User create port
- Role repository for CONTRATANTE

### Depends On

- **instituicao**, **user**, **role**, **auth** (tokens via create user)

## Known Gotchas

- Do not duplicate CNPJ/user validation here; trust downstream use cases but map errors clearly.
- If CONTRATANTE role seed is missing, onboarding fails hard — migrations must seed roles.
)
