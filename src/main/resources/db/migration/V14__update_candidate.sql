ALTER TABLE candidatos
    ADD COLUMN status_id BIGINT REFERENCES status (id);

UPDATE candidatos c
SET status_id = sub.id
FROM (
    SELECT s.id
    FROM status s
    INNER JOIN tipo_status ts ON s.tipo_status_id = ts.id
    WHERE ts.codigo = 'CANDIDATE' AND s.codigo = 'INSCRITO'
    LIMIT 1
) sub
WHERE c.status_id IS NULL;

ALTER TABLE candidatos ALTER COLUMN status_id SET NOT NULL;

ALTER TABLE candidatos
    ADD CONSTRAINT fk_candidato_status_status
        FOREIGN KEY (status_id)
            REFERENCES status (id);
