-- Histórico: migração legada que convertia tipos para INTEGER.
-- Baseline atual: todas as PKs e FKs usam BIGINT (BIGSERIAL) de forma consistente nas migrations anteriores.
-- Mantido como no-op para preservar a ordem de versões do Flyway.

SELECT 1;
