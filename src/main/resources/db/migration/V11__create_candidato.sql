CREATE TABLE candidatos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    cpf VARCHAR(14),
    email VARCHAR(255),

    processo_id BIGINT,
    cargo_id BIGINT,

    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_candidato_processo
        FOREIGN KEY (processo_id)
            REFERENCES processos_seletivos (id)
);
