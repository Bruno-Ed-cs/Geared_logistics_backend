CREATE TABLE notas_fiscais
(
    id               BIGSERIAL PRIMARY KEY,
    transacao_id     BIGINT                   NOT NULL REFERENCES transacoes (id),
    numero_documento VARCHAR(60)              NOT NULL,
    impostos         NUMERIC(10, 2),
    data_emissao     DATE,
    status           VARCHAR(20)              NOT NULL,
    criado_em        TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em    TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_notas_fiscais_transacao UNIQUE (transacao_id),
    CONSTRAINT uk_notas_fiscais_numero UNIQUE (numero_documento)
);
