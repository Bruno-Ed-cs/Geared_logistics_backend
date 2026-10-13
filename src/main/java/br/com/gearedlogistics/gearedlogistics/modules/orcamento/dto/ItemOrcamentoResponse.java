package br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.TipoItemOrcamento;

import java.math.BigDecimal;

public record ItemOrcamentoResponse(
        Long id,
        String descricao,
        TipoItemOrcamento tipo,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal subtotal,
        Long itemId,
        String itemNome
) {
}
