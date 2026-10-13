CREATE TABLE itens_orcamento
(
    id             BIGSERIAL PRIMARY KEY,
    orcamento_id   BIGINT                   NOT NULL REFERENCES orcamentos (id),
    descricao      VARCHAR(255)             NOT NULL,
    tipo           VARCHAR(20)              NOT NULL,
    quantidade     INTEGER                  NOT NULL,
    valor_unitario NUMERIC(10, 2)           NOT NULL,
    item_id        BIGINT REFERENCES itens (id),
    criado_em      TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_itens_orcamento_orcamento ON itens_orcamento (orcamento_id);
