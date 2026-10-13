CREATE TABLE orcamentos
(
    id               BIGSERIAL PRIMARY KEY,
    ordem_servico_id BIGINT                   NOT NULL REFERENCES ordens_servico (id),
    status           VARCHAR(20)              NOT NULL,
    data_validade    DATE,
    observacoes      VARCHAR(500),
    criado_em        TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em    TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_orcamentos_ordem_servico ON orcamentos (ordem_servico_id);
