-- Guarda de qual estoque o item foi debitado, para permitir o estorno em caso de cancelamento.
ALTER TABLE aplicacoes_item
    ADD COLUMN estoque_id BIGINT REFERENCES estoques (id);

CREATE INDEX idx_aplicacoes_item_estoque ON aplicacoes_item (estoque_id);
