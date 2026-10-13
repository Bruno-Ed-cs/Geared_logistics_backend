CREATE TABLE pedidos
(
    id                 BIGSERIAL PRIMARY KEY,
    fornecedor_id      BIGINT                   NOT NULL REFERENCES fornecedores (id),
    estoque_destino_id BIGINT                   NOT NULL REFERENCES estoques (id),
    data_solicitacao   DATE                     NOT NULL,
    status             VARCHAR(20)              NOT NULL,
    criado_em          TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em      TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_pedidos_fornecedor ON pedidos (fornecedor_id);
CREATE INDEX idx_pedidos_status ON pedidos (status);
