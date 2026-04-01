ALTER TABLE candidatos
    ADD COLUMN status_id INTEGER;

ALTER TABLE candidatos
    ADD CONSTRAINT fk_candidate_status
        FOREIGN KEY (status_id)
            REFERENCES status(id);