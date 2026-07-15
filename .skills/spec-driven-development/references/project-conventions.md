# Project Conventions — seleto-ai-api

## Skill tree layout

- **Project-level:** `.skills/project-context/`, `.skills/spec-driven-development/`
- **Per module:** `SKILL.md` co-located with the module package (usually `core/domain/<module>/SKILL.md`;
  onboarding under `core/useCase/onboarding/`; security under `adapters/security/`)
- New module skill → place `SKILL.md` next to that module’s primary package and link it in
  `.skills/project-context/SKILL.md` module map

## Framework & language

- Java 21, Spring Boot 3.3.5, Maven (`./mvnw`)
- Hexagonal architecture under `com.seletoai`
- PostgreSQL + Flyway + Spring Data JPA
- Spring Security + JWT + `@PreAuthorize` method security
- Lombok for boilerplate; constructor injection preferred

## Package layout

```
src/main/java/com/seletoai/
├── Application.java
├── config/                    # Security, JWT, rate limit, DB
├── dto/<feature>/             # *DTO with nested records/builders
├── core/
│   ├── domain/<feature>/      # entities, pure rules, exceptions
│   ├── ports/in/<feature>/    # *UseCasePort
│   ├── ports/out/<feature>/   # *RepositoryPort
│   ├── useCase/<feature>/     # implementations
│   └── mapper/<feature>/      # *Mapper
└── adapters/
    ├── controller/<feature>/  # *Controller + GlobalExceptionHandler
    ├── persistence/<feature>/ # Spring*Repository + *RepositoryAdapter
    └── security/              # AuthenticatedUser, entry points
```

## Naming

| Kind | Pattern | Example |
|------|---------|---------|
| Entity | PascalCase, table via `@Table` | `ProcessoSeletivo` → `processos_seletivos` |
| Use case | verb + domain + `UseCase` | `PublicarProcessoUseCase` |
| In-port | same + `Port` | `PublicarProcessoUseCasePort` |
| Out-port | `*RepositoryPort` | `ProcessoSeletivoRepositoryPort` |
| Spring Data | `Spring*Repository` | `SpringProcessoSeletivoRepository` |
| Adapter | `*RepositoryAdapter` | `ProcessoSeletivoRepositoryAdapter` |
| Controller | `*Controller` | `ProcessoSeletivoController` |
| DTO holder | `*DTO` with nested types | `ProcessoSeletivoDTO.ProcessoSeletivoRequest` |
| Domain exception | Portuguese names | `RegraNegocioException` |
| Flyway | `V{n}__snake_description.sql` | `V18__processo_lifecycle_cargos_audit.sql` |

Language mix is intentional (PT domain + EN tech). **Match the feature folder you touch** (e.g. `instituicao` uses PT verbs: `Criar`, `Listar`).

## Class / service style

- `@Service` or `@Component` + `@RequiredArgsConstructor` on use cases
- `@RestController` + `@RequestMapping` on controllers
- `@Transactional` on write use cases
- Controllers depend on **ports**, not concrete use case classes (when a port exists)
- Map with dedicated `*Mapper` in `core/mapper`

## Representative module tree (`processoSeletivo`)

```
core/domain/processoSeletivo/
  ProcessoSeletivo.java
  ProcessoCargo.java
  ProcessoAuditoria.java
  ProcessoLifecycleRules.java
  ProcessoStatusCodes.java
  ProcessoEventos.java
  TipoProcesso.java
core/ports/in/processoSeletivo/
  ProcessoSeletivoUseCasePort.java
  PublicarProcessoUseCasePort.java
  ...
core/ports/out/processoSeletivo/
  ProcessoSeletivoRepositoryPort.java
  ProcessoCargoRepositoryPort.java
  ProcessoAuditoriaRepositoryPort.java
core/useCase/processoSeletivo/
  ProcessoSeletivoUseCase.java
  PublicarProcessoUseCase.java
  IniciarProcessoUseCase.java
  EncerrarProcessoUseCase.java
  CancelarProcessoUseCase.java
  AdicionarCargoAoProcessoUseCase.java
  ProcessoAuditoriaWriter.java
adapters/controller/processoSeletivo/ProcessoSeletivoController.java
adapters/persistence/processoSeletivo/
  SpringProcessoSeletivoRepository.java
  ProcessoSeletivoRepositoryAdapter.java
  ...
dto/processoSeletivo/ProcessoSeletivoDTO.java
```

## Security conventions

- Public: `/auth/**`, `/roles/**`, Swagger paths
- Everything else authenticated
- Role checks: `@PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")` (Spring adds `ROLE_` prefix)
- Tenant check: pass `AuthContext` into use cases; call `garantirAcessoInstituicao`

## Test conventions

- Location: `src/test/java/com/seletoai/...`
- Prefer unit tests on use cases with mocked ports
- Integration tests: `@SpringBootTest` + `application-test.yaml` when needed
- **TDD:** write failing tests first; do not edit tests to force green (see `tdd-immutable.md`)
- Run: `./mvnw test` (or targeted `-Dtest=...`)

## HTTP / API conventions

- REST under feature prefixes (e.g. `/api/processos`, `/auth`)
- Validation: Jakarta `@Valid` on request bodies; custom CNPJ validators under `dto/validation`
- Errors: consistent `ErroDTO.ErroResponse` via `GlobalExceptionHandler`
- Port: **8081** (not 8080)
)
