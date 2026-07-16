# Seleto AI — Requisitos Técnicos

## Stack atual

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.x |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | PostgreSQL |
| Migrações | Flyway |
| Autenticação | JWT (jjwt) + Refresh Token |
| Autorização | Spring Security + @PreAuthorize (RBAC) |
| Validação | Jakarta Validation (Bean Validation) |
| Docs API | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Utilitários | Lombok |

---

## Arquitetura

Hexagonal (Ports & Adapters):

```
Controllers (adapters/in) → Use Case Ports (in) → Use Cases → Repository Ports (out) → Adapters (out) → Spring Data
```

- **Domain**: entidades JPA + regras de negócio puras (`ProcessoLifecycleRules`, `CandidateStatusCodes`, etc.)
- **Ports**: interfaces que desacoplam use cases de infraestrutura
- **Adapters**: implementações concretas (Spring Data repos, Spring Security, etc.)
- **DTOs**: records imutáveis com validação; nunca expõem entidades diretamente nas respostas novas

---

## Variáveis de ambiente obrigatórias

```env
# Banco
DB_URL=jdbc:postgresql://<host>:5432/seleto_ai
DB_USERNAME=
DB_PASSWORD=

# JWT
JWT_SECRET=<mínimo 256 bits, gerado com openssl rand -base64 32>
JWT_EXPIRATION_MS=86400000   # 24h padrão

# Rate limit
RATE_LIMIT_MAX_REQUESTS=10
RATE_LIMIT_WINDOW_SECONDS=60

# E-mail (quando implementado)
MAIL_HOST=smtp.resend.com
MAIL_PORT=465
MAIL_USERNAME=resend
MAIL_PASSWORD=<RESEND_API_KEY>
MAIL_SMTP_AUTH=true
MAIL_SMTP_STARTTLS=false

# App
APP_BASE_URL=https://api.seletoai.com.br
```

---

## Serviços externos necessários

### Obrigatórios agora

| Serviço | Uso | Recomendação |
|---|---|---|
| **PostgreSQL** | Banco principal | Neon (free tier), Supabase, Railway, RDS |
| **Hosting API** | Subir o JAR | Railway, Fly.io, Render, EC2 t3.small |

### Necessários para e-mail (próxima fase)

| Serviço | Uso | Recomendação |
|---|---|---|
| **Resend** | E-mails transacionais | resend.com — integra via SMTP ou SDK |
| **Domínio** | Remetente verificado (`noreply@seletoai.com.br`) | Registro.br + verificação DNS no Resend |

### Necessários para documentos/IA (fase futura)

| Serviço | Uso | Recomendação |
|---|---|---|
| **Object Storage** | CVs, PDFs, editais | Cloudflare R2 (gratuito até 10GB), AWS S3, Supabase Storage |
| **LLM API** | Análise de currículo, insights, ranking | Anthropic Claude (claude-sonnet-4-6), OpenAI GPT-4o |
| **Queue/Worker** | Processar análises assíncronas | Redis + Spring Batch, ou AWS SQS |

---

## Domínios e DNS

### Estrutura de subdomínios sugerida

```
seletoai.com.br          → Landing page / portal candidato (frontend)
api.seletoai.com.br      → Esta API
app.seletoai.com.br      → Dashboard contratante (frontend)
```

### DNS mínimo para e-mail (Resend)

Resend exige records SPF, DKIM e DMARC no domínio do remetente. Criar em Registro.br:

```
TXT  @                   v=spf1 include:resend.com -all
TXT  resend._domainkey   <chave DKIM gerada no Resend>
TXT  _dmarc              v=DMARC1; p=none; rua=mailto:dmarc@seletoai.com.br
```

---

## Ambientes

| Ambiente | Propósito | Config |
|---|---|---|
| **local** | Desenvolvimento | `application.yaml` com defaults localhost |
| **staging** | Testes de integração / QA | Banco separado, JWT_SECRET próprio, Resend em modo test |
| **production** | Produção | Banco dedicado, rate limit mais restrito, logs estruturados |

---

## Requisitos de infraestrutura por fase

### Fase 1 — MVP atual (processo + candidatos)
- [ ] PostgreSQL em nuvem (Neon free tier ou Railway)
- [ ] Hosting do JAR (Railway ou Fly.io)
- [ ] CI/CD básico (GitHub Actions → build + deploy)
- [ ] HTTPS via cert gerenciado pelo provider

### Fase 2 — E-mail
- [ ] Conta Resend com domínio verificado
- [ ] DNS configurado (SPF, DKIM, DMARC)
- [ ] Templates de e-mail (HTML) para: inscrição confirmada, mudança de status, boas-vindas
- [ ] Variáveis `MAIL_*` configuradas no ambiente de produção
- [ ] Fila assíncrona para envio (Spring `@Async` + executor, ou Queue dedicada)

### Fase 3 — IA
- [ ] Chave de API Anthropic ou OpenAI
- [ ] Object storage para upload de CVs (Cloudflare R2 ou S3)
- [ ] Worker assíncrono para análise (pode começar com Spring Batch agendado)
- [ ] Novo campo `cv_url` em `candidatos` (migration)
- [ ] Novos campos em `insights` (score, justificativa, modelo usado)

### Fase 4 — Observabilidade
- [ ] Exportar métricas Actuator → Prometheus/Grafana (ou Datadog)
- [ ] Sentry para rastreamento de erros com stack trace
- [ ] Logs estruturados em JSON (Logback → Loki ou CloudWatch)
- [ ] Alertas de SLA (latência p95, error rate)

---

## Requisitos de segurança

- JWT_SECRET gerado aleatoriamente por ambiente, nunca em código
- Refresh tokens com TTL de 7 dias, revogáveis
- Rate limit por IP (já implementado)
- Validação CNPJ no cadastro de instituição
- Dados de CPF não trafegam em GET (apenas no POST de inscrição)
- CORS restrito a origens conhecidas (configurado em SecurityConfig)
- `open-in-view: false` para evitar lazy loading fora de transação
- Soft delete em todas as entidades (`deleted_at`)
- `DataIntegrityViolationException` capturado e retorna 409 (sem vazar SQL)

---

## Convenções de código

- Hexagonal: nunca chamar repositório diretamente do controller
- Regras de negócio em classes `*LifecycleRules` ou `*StatusCodes` (sem estado)
- Use cases anotados com `@Transactional` (escrita) ou `@Transactional(readOnly=true)` (leitura)
- DTOs são records imutáveis com `@Builder` quando necessário
- Migrations Flyway versionadas sequencialmente (`V1`, `V2`... `Vn`)
- Sem `ddl-auto: create-drop` em nenhum ambiente além de testes isolados

---

## Checklist pré-produção

- [ ] JWT_SECRET definido e seguro
- [ ] `DB_URL` apontando para banco de produção
- [ ] Flyway executa todas as migrations sem erro
- [ ] Rate limit ajustado para carga esperada
- [ ] CORS com origens de produção apenas
- [ ] Swagger desabilitado em produção (ou protegido por IP/auth)
- [ ] Variáveis de e-mail configuradas
- [ ] Certificado HTTPS válido
- [ ] Backup automático do PostgreSQL configurado
