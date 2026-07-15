---
name: user
description: Business rules, internal patterns, and communication contracts for the user
  module. Load before working on any feature in user registration / profiles.
---

# Module: user

**Path:** `core/domain/user`, `core/useCase/user`, `adapters/.../user`, `userRole`

## Responsibility

Create and persist users, assign roles, link optional instituição, and support login token
issuance after register.

## Business Rules

- Email is unique; password stored hashed (BCrypt via security config / mapper path).
- Role must exist (`roleId`); invalid → `RegraNegocioException`.
- **CONTRATANTE** requires `instituicaoId`; other roles may omit institution.
- On create: save user → save `UserRole` → issue access JWT + refresh token row.
- Soft-delete fields exist on `BaseEntity` (`deletedAt`) but not all queries filter it yet —
  be explicit when adding listings.

## Internal Patterns

**Structure:**
```
core/domain/user/User.java
core/domain/userRole/UserRole.java
core/ports/in/user/
  CreateUserUseCasePort.java
  LoginUseCasePort.java
core/ports/out/user/UserRepositoryPort.java
core/ports/out/userRole/UserRoleRepositoryPort.java
core/useCase/user/
  CreateUserUseCase.java
  LoginUseCase.java
core/mapper/user/UserMapper.java
core/mapper/userRole/UserRoleMapper.java
adapters/controller/user/UserController.java
adapters/persistence/user|userRole/...
dto/auth/AuthDTO.java   # RegisterRequest / AuthResponse shared with auth
```

**Naming:** EN verbs for this slice (`CreateUser`, `Login`).

**Key abstractions:**
- `User` entity with optional `Instituicao`
- `UserRole` join entity (userId + roleId)
- `UserMapper` / `UserRoleMapper`

## Relationships

### Emits

- New `User` + `UserRole` rows
- Auth response tokens (via JwtService + refresh port)

### Consumes

- Role existence from **role**
- Optional instituição from **instituicao**
- Called by **onboarding** after creating institution

### Depends On

- **role**, **instituicao**, **auth** (JWT + refresh token ports)

## Known Gotchas

- Registration DTO lives under `dto/auth`, not `dto/user`.
- Login and create both touch token infrastructure — keep password hashing in one place.
- Multi-tenant: new features that list users must filter by `instituicao_id` unless ADMIN.
)
