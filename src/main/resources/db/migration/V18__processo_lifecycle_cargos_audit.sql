-- Novos status de processo (tipo PROCESSO)
INSERT INTO status (nome, codigo, ordem, tipo_status_id, ativo, created_at)
SELECT 'Em andamento', 'EM_ANDAMENTO', 3, ts.id, TRUE, CURRENT_TIMESTAMP
FROM tipo_status ts
WHERE ts.codigo = 'PROCESSO'
  AND NOT EXISTS (
    SELECT 1 FROM status s WHERE s.tipo_status_id = ts.id AND s.codigo = 'EM_ANDAMENTO'
  );

INSERT INTO status (nome, codigo, ordem, tipo_status_id, ativo, created_at)
SELECT 'Cancelado', 'CANCELADO', 4, ts.id, TRUE, CURRENT_TIMESTAMP
FROM tipo_status ts
WHERE ts.codigo = 'PROCESSO'
  AND NOT EXISTS (
    SELECT 1 FROM status s WHERE s.tipo_status_id = ts.id AND s.codigo = 'CANCELADO'
  );

UPDATE status s
SET ordem = 5
FROM tipo_status ts
WHERE s.tipo_status_id = ts.id
  AND ts.codigo = 'PROCESSO'
  AND s.codigo = 'ENCERRADO';

ALTER TABLE processos_seletivos
    ADD COLUMN IF NOT EXISTS tipo_processo VARCHAR(32) NOT NULL DEFAULT 'PUBLICO';

CREATE TABLE IF NOT EXISTS processo_cargos (
    id BIGSERIAL PRIMARY KEY,
    processo_id BIGINT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_processo_cargo_processo
        FOREIGN KEY (processo_id)
            REFERENCES processos_seletivos (id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_processo_cargos_processo_id ON processo_cargos (processo_id);

ALTER TABLE processos_seletivos
    ADD COLUMN IF NOT EXISTS status_id BIGINT REFERENCES status (id);

UPDATE processos_seletivos p
SET status_id = s.id
FROM status s
         INNER JOIN tipo_status ts ON s.tipo_status_id = ts.id
WHERE ts.codigo = 'PROCESSO'
  AND s.codigo = p.status
  AND p.status_id IS NULL;

UPDATE processos_seletivos p
SET status_id = s.id
FROM status s
         INNER JOIN tipo_status ts ON s.tipo_status_id = ts.id
WHERE ts.codigo = 'PROCESSO'
  AND s.codigo = 'RASCUNHO'
  AND p.status_id IS NULL;

ALTER TABLE processos_seletivos
    ALTER COLUMN status_id SET NOT NULL;

ALTER TABLE processos_seletivos
    DROP COLUMN IF EXISTS status;

CREATE INDEX IF NOT EXISTS idx_processos_seletivos_status_id ON processos_seletivos (status_id);

CREATE TABLE IF NOT EXISTS processo_auditoria (
    id BIGSERIAL PRIMARY KEY,
    processo_id BIGINT NOT NULL,
    tipo_evento VARCHAR(64) NOT NULL,
    detalhe TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_processo_auditoria_processo
        FOREIGN KEY (processo_id)
            REFERENCES processos_seletivos (id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_processo_auditoria_processo_id ON processo_auditoria (processo_id);
