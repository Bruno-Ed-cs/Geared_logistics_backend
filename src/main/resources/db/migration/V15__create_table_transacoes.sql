CREATE TABLE transacoes
(
    id               BIGSERIAL PRIMARY KEY,
    tipo             VARCHAR(20)              NOT NULL,
    cliente_id       BIGINT                   NOT NULL REFERENCES clientes (id),
    ordem_servico_id BIGINT REFERENCES ordens_servico (id),
    data_realizacao  DATE                     NOT NULL,
    valor_total      NUMERIC(10, 2)           NOT NULL,
    status           VARCHAR(20)              NOT NULL,
    criado_em        TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em    TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_transacoes_cliente ON transacoes (cliente_id);
CREATE INDEX idx_transacoes_ordem_servico ON transacoes (ordem_servico_id);

-- Subclasses (herança JOINED)
CREATE TABLE vendas
(
    id              BIGINT PRIMARY KEY REFERENCES transacoes (id),
    forma_pagamento VARCHAR(30)
);

CREATE TABLE pagamentos
(
    id               BIGINT PRIMARY KEY REFERENCES transacoes (id),
    valor_pago       NUMERIC(10, 2) NOT NULL DEFAULT 0,
    status_pagamento VARCHAR(30)    NOT NULL,
    data_quitacao    DATE
);

CREATE TABLE acordos_comerciais
(
    id              BIGINT PRIMARY KEY REFERENCES transacoes (id),
    condicoes       VARCHAR(500),
    numero_parcelas INTEGER
);
