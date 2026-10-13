CREATE TABLE aplicacoes_item
(
    id               BIGSERIAL PRIMARY KEY,
    ordem_servico_id BIGINT                   NOT NULL REFERENCES ordens_servico (id),
    item_id          BIGINT                   NOT NULL REFERENCES itens (id),
    quantidade       INTEGER                  NOT NULL,
    preco_no_momento NUMERIC(10, 2)           NOT NULL,
    criado_em        TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em    TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_aplicacoes_item_ordem_servico ON aplicacoes_item (ordem_servico_id);
CREATE INDEX idx_aplicacoes_item_item ON aplicacoes_item (item_id);
