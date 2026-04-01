CREATE TABLE candidate_status (
                                  id INTEGER PRIMARY KEY,
                                  code VARCHAR(50) UNIQUE NOT NULL,
                                  name VARCHAR(100) NOT NULL,
                                  description TEXT,
                                  active BOOLEAN DEFAULT TRUE,

                                  created_at TIMESTAMP,
                                  updated_at TIMESTAMP,
                                  deleted_at TIMESTAMP
);

-- Seeds padrão
INSERT INTO candidate_status (id, code, name) VALUES
                                                  (gen_random_uuid(), 'INSCRITO', 'Inscrito'),
                                                  (gen_random_uuid(), 'EM_ANALISE', 'Em análise'),
                                                  (gen_random_uuid(), 'APROVADO', 'Aprovado'),
                                                  (gen_random_uuid(), 'REPROVADO', 'Reprovado');