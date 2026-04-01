-- CANDIDATE
INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Inscrito', 'INSCRITO', 1, id FROM tipo_status WHERE codigo = 'CANDIDATE';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Em análise', 'EM_ANALISE', 2, id FROM tipo_status WHERE codigo = 'CANDIDATE';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Classificado', 'CLASSIFICADO', 3, id FROM tipo_status WHERE codigo = 'CANDIDATE';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Aprovado', 'APROVADO', 4, id FROM tipo_status WHERE codigo = 'CANDIDATE';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Reprovado', 'REPROVADO', 5, id FROM tipo_status WHERE codigo = 'CANDIDATE';

-- DOCUMENTO
INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Enviado', 'ENVIADO', 1, id FROM tipo_status WHERE codigo = 'DOCUMENTO';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Em validação', 'EM_VALIDACAO', 2, id FROM tipo_status WHERE codigo = 'DOCUMENTO';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Aprovado', 'APROVADO', 3, id FROM tipo_status WHERE codigo = 'DOCUMENTO';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Recusado', 'RECUSADO', 4, id FROM tipo_status WHERE codigo = 'DOCUMENTO';

-- PROCESSO
INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Rascunho', 'RASCUNHO', 1, id FROM tipo_status WHERE codigo = 'PROCESSO';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Publicado', 'PUBLICADO', 2, id FROM tipo_status WHERE codigo = 'PROCESSO';

INSERT INTO status (nome, codigo, ordem, tipo_status_id)
SELECT 'Encerrado', 'ENCERRADO', 3, id FROM tipo_status WHERE codigo = 'PROCESSO';