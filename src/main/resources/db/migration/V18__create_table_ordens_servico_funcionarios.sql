CREATE TABLE ordens_servico_funcionarios
(
    ordem_servico_id BIGINT NOT NULL REFERENCES ordens_servico (id),
    funcionario_id   BIGINT NOT NULL REFERENCES funcionarios (id),
    PRIMARY KEY (ordem_servico_id, funcionario_id)
);
