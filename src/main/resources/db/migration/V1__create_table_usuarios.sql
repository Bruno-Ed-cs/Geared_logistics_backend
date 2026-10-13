CREATE TABLE usuarios
(
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(150)             NOT NULL,
    email         VARCHAR(180)             NOT NULL,
    senha         VARCHAR(255)             NOT NULL,
    perfil        VARCHAR(20)              NOT NULL,
    ativo         BOOLEAN                  NOT NULL DEFAULT TRUE,
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL,
    atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_usuarios_email UNIQUE (email)
);
