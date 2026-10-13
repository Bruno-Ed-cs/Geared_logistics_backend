package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id,
        Long itemId,
        String itemNome,
        TipoItem itemTipo,
        Integer quantidadeSolicitada,
        BigDecimal precoNegociado,
        BigDecimal subtotal
) {
}
