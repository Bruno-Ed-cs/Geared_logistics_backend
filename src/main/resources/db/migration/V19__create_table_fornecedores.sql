CREATE TABLE fornecedores
(
    id            BIGSERIAL PRIMARY KEY,
    nome_empresa  VARCHAR(150)             NOT NULL,
    cnpj          VARCHAR(20)              NOT NULL,
    email         VARCHAR(180),
    telefone      VARCHAR(20),
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_fornecedores_cnpj UNIQUE (cnpj)
);
