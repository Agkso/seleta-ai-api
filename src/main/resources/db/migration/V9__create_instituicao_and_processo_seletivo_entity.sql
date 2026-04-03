CREATE TABLE IF NOT EXISTS instituicoes (
    id BIGSERIAL PRIMARY KEY,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS processos_seletivos (
    id BIGSERIAL PRIMARY KEY,
    instituicao_id BIGINT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    numero_edital VARCHAR(50) NOT NULL UNIQUE,
    data_inicio_inscricao TIMESTAMP NOT NULL,
    data_fim_inscricao TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'RASCUNHO',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_processo_instituicao
        FOREIGN KEY (instituicao_id)
            REFERENCES instituicoes (id)
            ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_processos_status ON processos_seletivos (status);
CREATE INDEX IF NOT EXISTS idx_processos_instituicao ON processos_seletivos (instituicao_id);
