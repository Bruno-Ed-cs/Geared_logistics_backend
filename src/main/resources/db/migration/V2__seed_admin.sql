-- Usuário administrador inicial (bootstrap).
-- Login: admin@gearedlogistics.com / admin123
-- IMPORTANTE: troque a senha após o primeiro acesso.
INSERT INTO usuarios (nome, email, senha, perfil, ativo, criado_em, atualizado_em)
VALUES ('Administrador',
        'admin@gearedlogistics.com',
        '$2a$10$xpXKRf/h/Nv9wkir05L5QOself7RYcWCBcVdsK/jlSZFkH087OQ5m',
        'ADMIN',
        TRUE,
        NOW(),
        NOW())
ON CONFLICT (email) DO NOTHING;
