package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;

import java.math.BigDecimal;

public record AplicacaoItemResponse(
        Long id,
        Long itemId,
        String itemNome,
        TipoItem itemTipo,
        Integer quantidade,
        BigDecimal precoNoMomento,
        BigDecimal subtotal
) {
}
