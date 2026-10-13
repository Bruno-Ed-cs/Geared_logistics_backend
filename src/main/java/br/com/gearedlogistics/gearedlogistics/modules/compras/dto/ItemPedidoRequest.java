package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ItemPedidoRequest(
        @NotNull
        Long itemId,

        @NotNull @Positive
        Integer quantidadeSolicitada,

        @NotNull @PositiveOrZero
        BigDecimal precoNegociado
) {
}
