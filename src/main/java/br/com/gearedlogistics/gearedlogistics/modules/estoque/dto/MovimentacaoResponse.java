package br.com.gearedlogistics.gearedlogistics.modules.estoque.dto;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;

import java.time.Instant;

public record MovimentacaoResponse(
        Long id,
        Long estoqueId,
        String estoqueNome,
        Long itemId,
        String itemNome,
        TipoMovimentacao tipo,
        Integer quantidade,
        Integer quantidadeResultante,
        String motivo,
        Instant dataHora
) {
}
