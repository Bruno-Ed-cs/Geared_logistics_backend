CREATE TABLE servicos
(
    id                BIGSERIAL PRIMARY KEY,
    ordem_servico_id  BIGINT                   NOT NULL REFERENCES ordens_servico (id),
    descricao         VARCHAR(255)             NOT NULL,
    valor_mao_de_obra NUMERIC(10, 2)           NOT NULL,
    criado_em         TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em     TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_servicos_ordem_servico ON servicos (ordem_servico_id);
