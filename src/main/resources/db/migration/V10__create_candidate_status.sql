CREATE TABLE candidate_status (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

INSERT INTO candidate_status (code, name) VALUES
    ('INSCRITO', 'Inscrito'),
    ('EM_ANALISE', 'Em análise'),
    ('APROVADO', 'Aprovado'),
    ('REPROVADO', 'Reprovado');
