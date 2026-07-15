---
name: role
description: Business rules, internal patterns, and communication contracts for the role
  module. Load before working on any feature in roles / RBAC.
---

# Module: role

**Path:** `core/domain/role`, `core/useCase/role`, `adapters/.../role|roles`

## Responsibility

Provide the RBAC role catalog and listing API used at registration and authorization.

## Business Rules

- Canonical role names in `RoleNomes`: `ADMIN`, `CONTRATANTE`, `PRESTADOR`, `SUPORTE`.
- Roles are seeded via Flyway (`V5__seed_roles.sql`); do not invent free-form names in code.
- `/roles` is public (needed for registration UI).
- Spring Security roles use `hasAnyRole('ADMIN', ...)` which expects authorities `ROLE_ADMIN`, etc.

## Internal Patterns

**Structure:**
```
core/domain/role/Role.java
core/domain/role/RoleNomes.java
core/ports/in/role/RolesUseCasePort.java
core/ports/out/role/RoleRepositoryPort.java
core/useCase/role/RolesUseCase.java
adapters/controller/roles/RoleController.java
adapters/persistence/role/...
```

**Naming:** `RoleNomes` constants; controller package is plural `roles`.

**Key abstractions:**
- `Role` entity
- `RoleNomes` constants (single source of name strings)

## Relationships

### Emits

- Role list for clients; `findByName` / `findById` for other modules

### Consumes

- None (leaf catalog)

### Depends On

- Persistence only; used by **user**, **onboarding**, **security**

## Known Gotchas

- Controller folder is `roles` (plural) while domain package is `role` (singular).
- Changing role names breaks seeds, JWT authorities, and `@PreAuthorize` strings together.
)
