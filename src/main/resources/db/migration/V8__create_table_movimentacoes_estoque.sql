CREATE TABLE movimentacoes_estoque
(
    id                    BIGSERIAL PRIMARY KEY,
    estoque_id            BIGINT                   NOT NULL REFERENCES estoques (id),
    item_id               BIGINT                   NOT NULL REFERENCES itens (id),
    tipo                  VARCHAR(20)              NOT NULL,
    quantidade            INTEGER                  NOT NULL,
    quantidade_resultante INTEGER                  NOT NULL,
    motivo                VARCHAR(255),
    criado_em             TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em         TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_movimentacoes_item ON movimentacoes_estoque (item_id);
CREATE INDEX idx_movimentacoes_estoque ON movimentacoes_estoque (estoque_id);
