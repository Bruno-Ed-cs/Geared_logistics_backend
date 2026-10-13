CREATE TABLE clientes
(
    id                 BIGSERIAL PRIMARY KEY,
    nome               VARCHAR(150)             NOT NULL,
    cpf_cnpj           VARCHAR(20)              NOT NULL,
    email              VARCHAR(180),
    profissao          VARCHAR(100),
    informacao_contato VARCHAR(255),
    logradouro         VARCHAR(150),
    numero             VARCHAR(20),
    bairro             VARCHAR(100),
    cidade             VARCHAR(100),
    estado             VARCHAR(2),
    cep                VARCHAR(10),
    criado_em          TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em      TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_clientes_cpf_cnpj UNIQUE (cpf_cnpj)
);

CREATE TABLE clientes_telefones
(
    cliente_id BIGINT      NOT NULL REFERENCES clientes (id) ON DELETE CASCADE,
    telefone   VARCHAR(20) NOT NULL,
    PRIMARY KEY (cliente_id, telefone)
);
