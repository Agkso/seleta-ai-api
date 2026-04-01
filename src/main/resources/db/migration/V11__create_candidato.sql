CREATE TABLE candidatos (
                            id INTEGER PRIMARY KEY,
                            nome VARCHAR(255),
                            cpf VARCHAR(14),
                            email VARCHAR(255),

                            status_id INTEGER NOT NULL,
                            processo_id INTEGER,
                            cargo_id INTEGER,

                            created_at TIMESTAMP,
                            updated_at TIMESTAMP,
                            deleted_at TIMESTAMP,

                            CONSTRAINT fk_candidate_status
                                FOREIGN KEY (status_id)
                                    REFERENCES candidate_status(id)
);