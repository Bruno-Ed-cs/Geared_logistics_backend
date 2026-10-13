CREATE TABLE itens
(
    id                BIGSERIAL PRIMARY KEY,
    nome              VARCHAR(150)             NOT NULL,
    codigo_fabricante VARCHAR(60),
    tipo              VARCHAR(20)              NOT NULL,
    preco_custo       NUMERIC(10, 2),
    preco_venda       NUMERIC(10, 2),
    criado_em         TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em     TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_itens_codigo_fabricante UNIQUE (codigo_fabricante)
);
