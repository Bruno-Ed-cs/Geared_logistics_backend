CREATE TABLE itens_pedido
(
    id                   BIGSERIAL PRIMARY KEY,
    pedido_id            BIGINT                   NOT NULL REFERENCES pedidos (id),
    item_id              BIGINT                   NOT NULL REFERENCES itens (id),
    quantidade_solicitada INTEGER                 NOT NULL,
    preco_negociado      NUMERIC(10, 2)           NOT NULL,
    criado_em            TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em        TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_itens_pedido_pedido ON itens_pedido (pedido_id);
