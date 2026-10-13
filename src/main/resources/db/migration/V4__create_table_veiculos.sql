CREATE TABLE veiculos
(
    id             BIGSERIAL PRIMARY KEY,
    placa          VARCHAR(10)              NOT NULL,
    marca          VARCHAR(50),
    modelo         VARCHAR(50),
    tipo           VARCHAR(30),
    ano_fabricacao INTEGER,
    cliente_id     BIGINT                   NOT NULL REFERENCES clientes (id),
    criado_em      TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em  TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_veiculos_placa UNIQUE (placa)
);

CREATE INDEX idx_veiculos_cliente ON veiculos (cliente_id);
