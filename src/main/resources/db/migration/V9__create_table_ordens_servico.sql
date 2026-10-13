CREATE TABLE ordens_servico
(
    id                 BIGSERIAL PRIMARY KEY,
    cliente_id         BIGINT                   NOT NULL REFERENCES clientes (id),
    veiculo_id         BIGINT                   NOT NULL REFERENCES veiculos (id),
    descricao_problema VARCHAR(500)             NOT NULL,
    status             VARCHAR(20)              NOT NULL,
    data_conclusao     TIMESTAMP WITH TIME ZONE,
    criado_em          TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em      TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_ordens_servico_cliente ON ordens_servico (cliente_id);
CREATE INDEX idx_ordens_servico_veiculo ON ordens_servico (veiculo_id);
CREATE INDEX idx_ordens_servico_status ON ordens_servico (status);
