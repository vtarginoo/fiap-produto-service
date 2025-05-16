CREATE SEQUENCE produto_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE produto (
    id BIGINT PRIMARY KEY DEFAULT nextval('produto_id_seq'),
    nome VARCHAR(255) NOT NULL,
    sku VARCHAR(255) UNIQUE NOT NULL,
    preco NUMERIC(19,2) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    deleted_tmsp TIMESTAMP
);