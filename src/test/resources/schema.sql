CREATE SEQUENCE produto_id_seq START WITH 8 INCREMENT BY 1;

CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    sku VARCHAR(255) UNIQUE NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_tmsp TIMESTAMP
);