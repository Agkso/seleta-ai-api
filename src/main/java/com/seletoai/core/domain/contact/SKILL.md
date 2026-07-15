---
name: contact
description: Business rules for the legacy contact module. Load only if touching contacts table
  or user contact channels.
---

# Module: contact

**Path:** `core/domain/contact`, `adapters/persistence/contact`, `ports/out/contact`

## Responsibility

**Legacy / unused in product flows.** Entity + repository port/adapter for user contact channels
(phone, whatsapp, email) keyed by `userId`. No controller or use case exists.

## Business Rules

- Table `contacts`; belongs to a user (`user_id` required).
- Do not wire into onboarding/auth unless a product decision revives this module.
- If revived: treat as 1:1 or 1:N user contacts and enforce tenancy via the parent user.

## Internal Patterns

**Structure:**
```
core/domain/contact/Contact.java
core/ports/out/contact/ContactRepositoryPort.java
adapters/persistence/contact/
  SpringContactRepository.java
  ContactRepositoryAdapter.java
```

**Naming:** EN entity `Contact`.

**Key abstractions:**
- Minimal port (`save` only today)

## Relationships

### Emits

- None in current flows

### Consumes

- None

### Depends On

- Intended link to **user** via `userId` (no FK entity mapping enforced in domain code)

## Known Gotchas

- Scaffold only — avoid expanding without a real use case.
- Prefer deleting or documenting dead code if product confirms permanent deprecation.
)
