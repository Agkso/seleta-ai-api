---
name: auth
description: Business rules, internal patterns, and communication contracts for the auth
  module. Load before working on any feature in auth / JWT / refresh tokens / login.
---

# Module: auth

**Path:** `core/useCase/auth`, `core/domain/auth`, `config/jwt`, `adapters` security integration

## Responsibility

Authenticate users (login), issue JWT access tokens, create and rotate refresh tokens, and expose
the authenticated principal as `AuthContext` for tenant checks in other modules.

## Business Rules

- Access token is JWT signed with `jwt.secret` (env `JWT_SECRET`, mandatory at startup).
- Refresh tokens are UUID strings persisted with expiry (typically 7 days) and `revoked` flag.
- Login validates credentials via Spring Security / user details; invalid credentials →
  `CredenciaisInvalidasException` / 401.
- `/auth/**` is public in `SecurityConfig`; rate limiting still applies via `RateLimitFilter`.
- Registration lives primarily in **user** (`CreateUserUseCase`) but returns the same
  `AuthDTO.AuthResponse` (access + refresh).

## Internal Patterns

**Structure:**
```
core/domain/auth/
  AuthContext.java          # record: userId, instituicaoId, admin + garantirAcessoInstituicao
  RefreshToken.java
core/ports/in/auth/
  CreateRefreshTokenUseCasePort.java
  RefreshTokenUseCasePort.java
core/ports/out/auth/
  RefreshTokenRepositoryPort.java
core/useCase/auth/
  CreateRefreshTokenUseCase.java
  RefreshTokenUseCase.java
core/useCase/user/
  LoginUseCase.java         # login entry (paired with auth)
config/jwt/
  JwtService.java
  JwtFilter.java
adapters/controller/auth/AuthController.java
adapters/persistence/auth/
  SpringRefreshTokenRepository.java
  RefreshTokenRepositoryAdapter.java
dto/auth/AuthDTO.java
```

**Naming:** `*RefreshToken*`, `JwtService`, `AuthDTO.*Request/Response`.

**Key abstractions:**
- `AuthContext` — domain record for multi-tenant authorization (not a Spring Security type).
- `JwtService` — token generation/validation.
- `AuthenticatedUser` (security adapter) — principal mapped into `AuthContext` in controllers.

## Relationships

### Emits

- JWT access tokens and refresh token rows for successful login/register/refresh.
- Populated security context for downstream controllers.

### Consumes

- User credentials and roles from **user** / **role**.
- Institution id on the user for `AuthContext.instituicaoId`.

### Depends On

- **user** (lookup by email, password check)
- **role** / user roles (ADMIN flag for `AuthContext.admin`)
- **security** config (filters, password encoder)

## Known Gotchas

- Controllers must map `AuthenticatedUser` → `AuthContext` consistently before calling use cases.
- `AuthContext.garantirAcessoInstituicao` allows ADMIN; non-admins without `instituicaoId` always fail tenant checks.
- Do not put business rules only in filters; keep tenant rules in use cases via `AuthContext`.
)
