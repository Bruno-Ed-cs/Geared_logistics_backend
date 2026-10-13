CREATE TABLE estoques
(
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(100)             NOT NULL,
    localizacao   VARCHAR(150),
    capacidade    INTEGER,
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL
);
