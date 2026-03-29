CREATE TABLE IF NOT EXISTS instituicoes (
    id SERIAL PRIMARY KEY, -- Equivalente ao Integer Auto-Generated
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255) NOT NULL,

    -- Colunas padrão assumidas do seu BaseEntity (ajuste se o seu BaseEntity tiver nomes diferentes)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 2. Tabela Processos Seletivos
CREATE TABLE IF NOT EXISTS processos_seletivos (
    id SERIAL PRIMARY KEY,
    instituicao_id INTEGER NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    numero_edital VARCHAR(50) NOT NULL UNIQUE,
    data_inicio_inscricao TIMESTAMP NOT NULL,
    data_fim_inscricao TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'RASCUNHO',

    -- Colunas padrão do BaseEntity
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    -- Constraint de Segurança Jurídica: Impede que uma instituição seja apagada se tiver editais vinculados
    CONSTRAINT fk_processo_instituicao
        FOREIGN KEY (instituicao_id)
        REFERENCES instituicoes (id)
        ON DELETE RESTRICT
);

-- Indexação para a IA e Buscas rápidas no portal do candidato
CREATE INDEX IF NOT EXISTS idx_processos_status ON processos_seletivos(status);
CREATE INDEX IF NOT EXISTS idx_processos_instituicao ON processos_seletivos(instituicao_id);