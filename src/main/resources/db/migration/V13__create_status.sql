CREATE TABLE status (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL,
    descricao TEXT,
    ordem INTEGER,
    ativo BOOLEAN DEFAULT TRUE,

    tipo_status_id BIGINT NOT NULL,

    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_tipo_status
        FOREIGN KEY (tipo_status_id)
            REFERENCES tipo_status (id)
);
