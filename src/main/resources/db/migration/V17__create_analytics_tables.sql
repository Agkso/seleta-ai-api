CREATE TABLE IF NOT EXISTS analytics_snapshots (
    id SERIAL PRIMARY KEY,
    process_id INTEGER NOT NULL,
    date DATE NOT NULL,
    total_inscricoes INTEGER,
    total_aprovados INTEGER,
    total_reprovados INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_analytics_snapshots_processo
        FOREIGN KEY (process_id)
            REFERENCES processos_seletivos (id)
            ON DELETE CASCADE,
    CONSTRAINT uq_analytics_snapshots_process_date UNIQUE (process_id, date)
);

CREATE INDEX IF NOT EXISTS idx_analytics_snapshots_process_id ON analytics_snapshots (process_id);

CREATE TABLE IF NOT EXISTS insights (
    id SERIAL PRIMARY KEY,
    process_id INTEGER NOT NULL,
    type VARCHAR(255),
    title VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_insights_processo
        FOREIGN KEY (process_id)
            REFERENCES processos_seletivos (id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_insights_process_id ON insights (process_id);

CREATE TABLE IF NOT EXISTS alerts (
    id SERIAL PRIMARY KEY,
    process_id INTEGER NOT NULL,
    type VARCHAR(255),
    message VARCHAR(2000),
    resolved BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_alerts_processo
        FOREIGN KEY (process_id)
            REFERENCES processos_seletivos (id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_alerts_process_id ON alerts (process_id);
