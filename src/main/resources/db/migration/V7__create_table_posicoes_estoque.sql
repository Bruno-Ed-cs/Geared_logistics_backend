CREATE TABLE posicoes_estoque
(
    id               BIGSERIAL PRIMARY KEY,
    estoque_id       BIGINT                   NOT NULL REFERENCES estoques (id),
    item_id          BIGINT                   NOT NULL REFERENCES itens (id),
    quantidade_atual INTEGER                  NOT NULL DEFAULT 0,
    criado_em        TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em    TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_posicao_estoque_item UNIQUE (estoque_id, item_id)
);
