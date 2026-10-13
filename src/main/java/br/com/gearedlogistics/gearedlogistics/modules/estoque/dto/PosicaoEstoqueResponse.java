package br.com.gearedlogistics.gearedlogistics.modules.estoque.dto;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;

import java.time.Instant;

public record PosicaoEstoqueResponse(
        Long id,
        Long estoqueId,
        Long itemId,
        String itemNome,
        TipoItem itemTipo,
        Integer quantidadeAtual,
        boolean disponivel,
        Instant atualizadoEm
) {
}
