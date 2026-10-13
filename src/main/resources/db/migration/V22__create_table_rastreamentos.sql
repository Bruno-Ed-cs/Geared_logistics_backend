CREATE TABLE rastreamentos
(
    id                 BIGSERIAL PRIMARY KEY,
    pedido_id          BIGINT                   NOT NULL REFERENCES pedidos (id),
    codigo_rastreio    VARCHAR(60)              NOT NULL,
    status_localizacao VARCHAR(255),
    data_atualizacao   TIMESTAMP WITH TIME ZONE,
    criado_em          TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em      TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_rastreamentos_pedido UNIQUE (pedido_id)
);
