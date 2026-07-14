ALTER TABLE users
    ADD COLUMN IF NOT EXISTS instituicao_id BIGINT NULL;

ALTER TABLE users
    ADD CONSTRAINT fk_users_instituicao
        FOREIGN KEY (instituicao_id)
            REFERENCES instituicoes (id)
            ON DELETE RESTRICT;

CREATE INDEX IF NOT EXISTS idx_users_instituicao ON users (instituicao_id);
