CREATE TABLE IF NOT EXISTS contacts (
    id SERIAL PRIMARY KEY,

    phone VARCHAR(20),
    whatsapp VARCHAR(20),
    email VARCHAR(150),

    user_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_contact_user FOREIGN KEY (user_id) REFERENCES users(id)
);