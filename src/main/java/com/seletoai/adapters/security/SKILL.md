---
name: security
description: Business rules, internal patterns, and communication contracts for security config
  (JWT filters, CORS, rate limit, method security). Load before changing authz or public routes.
---

# Module: security

**Path:** `config/SecurityConfig.java`, `config/jwt`, `config/ratelimit`, `adapters/security`

## Responsibility

HTTP security filter chain, CORS, JWT authentication filter, rate limiting, password encoding,
and REST entry/denied handlers. Complements domain tenancy (`AuthContext`) with Spring Security
roles.

## Business Rules

- Stateless sessions (`SessionCreationPolicy.STATELESS`).
- Public matchers: `/auth/**`, `/roles`, `/roles/**`, Swagger/OpenAPI paths, OPTIONS.
- All other routes require authentication.
- Filter order: `RateLimitFilter` → `JwtFilter` → `UsernamePasswordAuthenticationFilter`.
- Method security enabled (`@EnableMethodSecurity`); use `@PreAuthorize` on controllers.
- Password encoder: BCrypt.
- CORS allows localhost:3000 / 127.0.0.1:3000 / localhost:8081 (tighten for production).
- Rate limit defaults: 10 requests / 60s window (env-overridable).

## Internal Patterns

**Structure:**
```
config/SecurityConfig.java
config/jwt/JwtFilter.java
config/jwt/JwtService.java
config/ratelimit/RateLimitFilter.java
adapters/security/
  AuthenticatedUser.java
  CustomUserDetailsService.java
  RestAuthEntryPoint.java
  RestAccessDeniedHandler.java
adapters/controller/error/GlobalExceptionHandler.java
```

**Naming:** Spring Security idioms + `Rest*` handlers for JSON errors.

**Key abstractions:**
- `AuthenticatedUser` principal
- `JwtFilter` populates security context from Bearer token
- Domain exceptions still handled by `GlobalExceptionHandler`

## Relationships

### Emits

- Authenticated principal for controllers
- 401/403 JSON via entry points / access denied handler

### Consumes

- User details from **user** / roles
- JWT secret from config

### Depends On

- **auth**, **user**, **role**

## Known Gotchas

- `@PreAuthorize("hasAnyRole('ADMIN')")` expects authority `ROLE_ADMIN` — ensure role loading adds prefix.
- Domain tenancy is **not** automatic from Security alone; still call `AuthContext.garantirAcessoInstituicao`.
- Swagger is ignored in `WebSecurityCustomizer` **and** permitted in the filter chain — keep both in sync when changing docs paths.
- Changing public routes can accidentally expose tenant data — review carefully.
)
