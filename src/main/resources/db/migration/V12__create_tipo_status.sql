CREATE TABLE tipo_status (
                             id SERIAL PRIMARY KEY,
                             codigo VARCHAR(50) NOT NULL UNIQUE,
                             nome VARCHAR(100) NOT NULL,
                             descricao TEXT,
                             ativo BOOLEAN DEFAULT TRUE,

                             created_at TIMESTAMP,
                             updated_at TIMESTAMP,
                             deleted_at TIMESTAMP
);