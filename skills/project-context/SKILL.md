---
name: project-context
description: Use to understand this system's domain, core entities, code conventions, and module map
  before any development task. Load this first when starting any feature, fix, or refactor.
---

# Project Context: seleto-ai-api

## Domain

**Seleto AI** backend for institutions that run **processos seletivos** (selection/hiring contests)
in a **public procurement / licitações-adjacent** context: public bodies, universities, and companies
publish openings (with optional formal edital), manage cargos, receive candidate inscriptions, and
consume dashboard analytics.

Product surface today is recruitment lifecycle + multi-tenant instituições; AI-assisted edital
analysis is a stated future direction (README), not yet a core runtime module.

## Core Entities

| Entity | Role |
|--------|------|
| `User` | Account with email/password; optional `instituicao_id` |
| `Role` / `UserRole` | RBAC: ADMIN, CONTRATANTE, PRESTADOR, SUPORTE |
| `Instituicao` | Tenant org (CNPJ, razão social, nome fantasia) |
| `ProcessoSeletivo` | Selection process owned by an instituição; lifecycle status |
| `ProcessoCargo` | Positions/vacancies on a process (required before publish) |
| `ProcessoAuditoria` | Lifecycle audit trail (event string constants) |
| `Candidate` | Inscription linked to processo + cargo |
| `Status` / `TipoStatus` | Catalog of domain statuses (PROCESSO, CANDIDATE, …) |
| `RefreshToken` | Long-lived session token for JWT refresh |
| `Contact` | User contact channels — **legacy / unused in flows** |
| `AnalyticsSnapshot` / `Insight` / `Alert` | Per-process dashboard metrics |

Shared base: `BaseEntity` (`id`, `createdAt`, `updatedAt`, `deletedAt`).

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.3.5
- **Architecture:** Hexagonal (ports & adapters) — package `com.seletoai`
- **Persistence:** Spring Data JPA + PostgreSQL; schema via **Flyway** (`ddl-auto: validate`)
- **Security:** Spring Security + JWT (jjwt) + BCrypt; method security `@PreAuthorize`
- **API:** REST + springdoc OpenAPI (Swagger UI on `:8081`)
- **Other:** Lombok, Actuator, JavaMail, simple in-memory rate limit filter
- **Tests:** JUnit 5 via `spring-boot-starter-test` (minimal suite today)
- **Build:** Maven Wrapper (`./mvnw`)

## Code Conventions

- **Layers (always):**
  - `core/domain` — entities + pure rules (no Spring unless unavoidable)
  - `core/ports/in` — use-case interfaces (`*UseCasePort`)
  - `core/ports/out` — repository/outbound interfaces (`*RepositoryPort`)
  - `core/useCase` — implementations (`@Service` / `@Component`)
  - `core/mapper` — domain ↔ DTO
  - `adapters/controller` — REST
  - `adapters/persistence` — `Spring*Repository` + `*RepositoryAdapter`
  - `adapters/security` — auth principal, handlers
  - `dto` — request/response records (often nested in `*DTO`)
  - `config` — Security, JWT, DB, rate limit
- **Naming:** `CriarXUseCase` / `CreateXUseCase` (mixed PT/EN — prefer matching the module’s existing language); ports end with `Port`; adapters end with `Adapter` or `RepositoryAdapter`.
- **DI:** constructor injection via Lombok `@RequiredArgsConstructor`.
- **Errors:** domain exceptions → `GlobalExceptionHandler` → `ErroDTO` (`RegraNegocioException` 400, `RecursoNaoEncontradoException` 404, `AcessoNegadoException` 403).
- **Auth context:** controllers build `AuthContext` from `AuthenticatedUser`; use cases call `authContext.garantirAcessoInstituicao(...)`.
- **Migrations:** only Flyway under `src/main/resources/db/migration/V*__*.sql` — never rely on Hibernate DDL mutate.
- **Tests:** under `src/test/java/com/seletoai/...` mirroring packages; write tests **before** implementation (see SDD TDD immutable).

## Module Map

| Module | Code slice (primary) | Skill |
|--------|----------------------|-------|
| auth | `core/useCase/auth`, `config/jwt`, refresh tokens, login | [skills/auth/SKILL.md](../auth/SKILL.md) |
| user | `core/domain/user`, create user, user↔role | [skills/user/SKILL.md](../user/SKILL.md) |
| role | roles catalog + `RoleController` | [skills/role/SKILL.md](../role/SKILL.md) |
| instituicao | CRUD instituição + CNPJ validation | [skills/instituicao/SKILL.md](../instituicao/SKILL.md) |
| processoSeletivo | processes, cargos, audit, lifecycle | [skills/processoSeletivo/SKILL.md](../processoSeletivo/SKILL.md) |
| processoSeletivo/lifecycle | publish/start/end/cancel + rules | [skills/processoSeletivo/lifecycle/SKILL.md](../processoSeletivo/lifecycle/SKILL.md) |
| candidate | inscriptions to process+cargo | [skills/candidate/SKILL.md](../candidate/SKILL.md) |
| onboarding | institution + CONTRATANTE bootstrap | [skills/onboarding/SKILL.md](../onboarding/SKILL.md) |
| analytics | dashboard snapshots/insights/alerts | [skills/analytics/SKILL.md](../analytics/SKILL.md) |
| contact | legacy contacts table (no flows) | [skills/contact/SKILL.md](../contact/SKILL.md) |
| status | shared status catalog | [skills/status/SKILL.md](../status/SKILL.md) |
| security | filter chain, rate limit, principals | [skills/security/SKILL.md](../security/SKILL.md) |

## Cross-Module Communication

- **Pattern:** **direct dependency injection + shared PostgreSQL** (no message bus, no inter-service HTTP).
- Use cases inject other modules via **ports** (`*UseCasePort`, `*RepositoryPort`), not concrete adapters.
- Lifecycle “events” (`ProcessoEventos.*`) are **audit string constants** written via `ProcessoAuditoriaWriter`, not Spring `ApplicationEvent`s.
- Prefer composing use cases (e.g. onboarding → `CriarInstituicao` + `CreateUser`) over leaking repository details across domains.

## Global Business Rules

1. **Multi-tenant by instituição:** non-admin users may only read/mutate resources whose `instituicao_id` matches `AuthContext.instituicaoId`. ADMIN bypasses via `AuthContext.admin`. Enforce on every process (and related) mutation/query that is tenant-scoped.
2. **CONTRATANTE requires institution:** registration without `instituicaoId` is rejected for role CONTRATANTE.
3. **Process lifecycle** is gated by `ProcessoLifecycleRules` + status catalog (`RASCUNHO` → `PUBLICADO` → `EM_ANDAMENTO` → `ENCERRADO` / `CANCELADO`).
4. **PUBLICO / UNIVERSIDADE** processes require formal `numeroEdital` before publish; at least one cargo is always required to publish.
5. **Inscriptions** only when process is `PUBLICADO` or `EM_ANDAMENTO` and current time is within inscription window; cargo must belong to the process.
6. **JWT_SECRET** is mandatory at runtime (fail-fast); passwords stored with BCrypt.
7. **Schema changes only via Flyway**; JPA validates, does not migrate.
)
