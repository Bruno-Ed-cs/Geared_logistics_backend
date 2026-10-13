-- ============================================================================
-- Dados de exemplo para desenvolvimento.
-- Idempotente: usa ON CONFLICT / WHERE NOT EXISTS para não duplicar nem quebrar
-- quando o banco já tem dados.
-- ============================================================================

-- Estoque -------------------------------------------------------------------
INSERT INTO estoques (nome, localizacao, capacidade, criado_em, atualizado_em)
SELECT 'Estoque Secundário', 'Prateleira B', 500, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoques WHERE nome = 'Estoque Secundário');

-- Clientes ------------------------------------------------------------------
INSERT INTO clientes (nome, cpf_cnpj, email, profissao, informacao_contato,
                      logradouro, numero, bairro, cidade, estado, cep, criado_em, atualizado_em)
VALUES ('Zeca Peças e Serviços', '168.995.350-09', 'zeca@email.com', 'Comerciante', 'cliente desde 2023',
        'Rua das Palmeiras', '120', 'Centro', 'Mossoró', 'RN', '59600-100', NOW(), NOW())
ON CONFLICT (cpf_cnpj) DO NOTHING;

INSERT INTO clientes (nome, cpf_cnpj, email, logradouro, numero, bairro, cidade, estado, cep,
                      criado_em, atualizado_em)
VALUES ('Oficina Beta LTDA', '45.678.901/0001-75', 'contato@oficinabeta.com',
        'Av. Rio Branco', '900', 'Centro', 'Natal', 'RN', '59000-200', NOW(), NOW())
ON CONFLICT (cpf_cnpj) DO NOTHING;

INSERT INTO clientes_telefones (cliente_id, telefone)
SELECT id, '(84) 98888-1000' FROM clientes WHERE cpf_cnpj = '168.995.350-09'
ON CONFLICT (cliente_id, telefone) DO NOTHING;

INSERT INTO clientes_telefones (cliente_id, telefone)
SELECT id, '(84) 3222-4000' FROM clientes WHERE cpf_cnpj = '45.678.901/0001-75'
ON CONFLICT (cliente_id, telefone) DO NOTHING;

-- Veículos ------------------------------------------------------------------
INSERT INTO veiculos (placa, marca, modelo, tipo, ano_fabricacao, cliente_id, criado_em, atualizado_em)
SELECT 'ZEC1A23', 'Volkswagen', 'Gol', 'Carro', 2019, id, NOW(), NOW()
FROM clientes WHERE cpf_cnpj = '168.995.350-09'
ON CONFLICT (placa) DO NOTHING;

INSERT INTO veiculos (placa, marca, modelo, tipo, ano_fabricacao, cliente_id, criado_em, atualizado_em)
SELECT 'BET2B34', 'Fiat', 'Strada', 'Caminhão', 2021, id, NOW(), NOW()
FROM clientes WHERE cpf_cnpj = '45.678.901/0001-75'
ON CONFLICT (placa) DO NOTHING;

-- Catálogo de itens ---------------------------------------------------------
INSERT INTO itens (nome, codigo_fabricante, tipo, preco_custo, preco_venda, criado_em, atualizado_em)
VALUES ('Filtro de óleo', 'FO-100', 'PECA', 20.00, 35.00, NOW(), NOW())
ON CONFLICT (codigo_fabricante) DO NOTHING;

INSERT INTO itens (nome, codigo_fabricante, tipo, preco_custo, preco_venda, criado_em, atualizado_em)
VALUES ('Correia dentada', 'CD-200', 'PECA', 45.00, 80.00, NOW(), NOW())
ON CONFLICT (codigo_fabricante) DO NOTHING;

INSERT INTO itens (nome, codigo_fabricante, tipo, preco_custo, preco_venda, criado_em, atualizado_em)
VALUES ('Fluido de freio', 'FF-300', 'MATERIAL', 15.00, 28.00, NOW(), NOW())
ON CONFLICT (codigo_fabricante) DO NOTHING;

-- Fornecedor ----------------------------------------------------------------
INSERT INTO fornecedores (nome_empresa, cnpj, email, telefone, criado_em, atualizado_em)
VALUES ('Distribuidora Gama', '12.345.678/0001-95', 'vendas@gama.com', '(84) 3333-9000', NOW(), NOW())
ON CONFLICT (cnpj) DO NOTHING;

-- Funcionários --------------------------------------------------------------
INSERT INTO funcionarios (nome, cpf_cnpj, matricula, cargo, especialidade, salario, data_admissao,
                          ativo, criado_em, atualizado_em)
VALUES ('Pedro Mecânico', '987.654.321-00', 'MEC-100', 'MECANICO', 'Motor', 3200.00, CURRENT_DATE - 400,
        TRUE, NOW(), NOW())
ON CONFLICT (matricula) DO NOTHING;

INSERT INTO funcionarios (nome, cpf_cnpj, matricula, cargo, nivel_acesso_admin, salario, data_admissao,
                          ativo, criado_em, atualizado_em)
VALUES ('Sofia Gerente', '111.222.333-96', 'GER-100', 'GERENTE', 5, 7500.00, CURRENT_DATE - 800,
        TRUE, NOW(), NOW())
ON CONFLICT (matricula) DO NOTHING;

-- Saldo inicial de estoque (posições) ---------------------------------------
INSERT INTO posicoes_estoque (estoque_id, item_id, quantidade_atual, criado_em, atualizado_em)
SELECT e.id, i.id, 40, NOW(), NOW()
FROM estoques e, itens i
WHERE e.nome = 'Estoque Secundário' AND i.codigo_fabricante = 'FO-100'
ON CONFLICT (estoque_id, item_id) DO NOTHING;

INSERT INTO posicoes_estoque (estoque_id, item_id, quantidade_atual, criado_em, atualizado_em)
SELECT e.id, i.id, 15, NOW(), NOW()
FROM estoques e, itens i
WHERE e.nome = 'Estoque Secundário' AND i.codigo_fabricante = 'CD-200'
ON CONFLICT (estoque_id, item_id) DO NOTHING;

-- Histórico de movimentação -------------------------------------------------
INSERT INTO movimentacoes_estoque (estoque_id, item_id, tipo, quantidade, quantidade_resultante,
                                   motivo, criado_em, atualizado_em)
SELECT e.id, i.id, 'ENTRADA', 40, 40, 'Carga inicial (seed)', NOW(), NOW()
FROM estoques e, itens i
WHERE e.nome = 'Estoque Secundário' AND i.codigo_fabricante = 'FO-100';

-- Pedido de compra de exemplo ----------------------------------------------
WITH novo_pedido AS (
    INSERT INTO pedidos (fornecedor_id, estoque_destino_id, data_solicitacao, status, criado_em, atualizado_em)
    SELECT f.id, e.id, CURRENT_DATE, 'SOLICITADO', NOW(), NOW()
    FROM fornecedores f, estoques e
    WHERE f.cnpj = '12.345.678/0001-95' AND e.nome = 'Estoque Secundário'
    RETURNING id
)
INSERT INTO itens_pedido (pedido_id, item_id, quantidade_solicitada, preco_negociado, criado_em, atualizado_em)
SELECT p.id, i.id, 10, 18.00, NOW(), NOW()
FROM novo_pedido p, itens i
WHERE i.codigo_fabricante = 'FO-100';

-- Ordem de serviço de exemplo ----------------------------------------------
WITH nova_os AS (
    INSERT INTO ordens_servico (cliente_id, veiculo_id, descricao_problema, status, criado_em, atualizado_em)
    SELECT c.id, v.id, 'Revisão preventiva (seed)', 'ABERTA', NOW(), NOW()
    FROM clientes c, veiculos v
    WHERE c.cpf_cnpj = '168.995.350-09' AND v.placa = 'ZEC1A23'
    RETURNING id
)
INSERT INTO servicos (ordem_servico_id, descricao, valor_mao_de_obra, criado_em, atualizado_em)
SELECT id, 'Troca de óleo e filtros', 120.00, NOW(), NOW()
FROM nova_os;

INSERT INTO ordens_servico_funcionarios (ordem_servico_id, funcionario_id)
SELECT os.id, f.id
FROM ordens_servico os, funcionarios f
WHERE os.descricao_problema = 'Revisão preventiva (seed)' AND f.matricula = 'MEC-100'
ON CONFLICT (ordem_servico_id, funcionario_id) DO NOTHING;

-- Orçamento de exemplo para a OS acima --------------------------------------
WITH novo_orcamento AS (
    INSERT INTO orcamentos (ordem_servico_id, status, data_validade, observacoes, criado_em, atualizado_em)
    SELECT os.id, 'RASCUNHO', CURRENT_DATE + 30, 'Proposta inicial (seed)', NOW(), NOW()
    FROM ordens_servico os
    WHERE os.descricao_problema = 'Revisão preventiva (seed)'
    RETURNING id
)
INSERT INTO itens_orcamento (orcamento_id, descricao, tipo, quantidade, valor_unitario, item_id,
                             criado_em, atualizado_em)
SELECT o.id, 'Troca de óleo e filtros', 'MAO_DE_OBRA', 1, 120.00, NULL, NOW(), NOW()
FROM novo_orcamento o;
