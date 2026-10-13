package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AplicacaoItemRequest(
        @NotNull
        Long itemId,

        @NotNull @Positive
        Integer quantidade,

        /** Estoque de onde o item será debitado. */
        @NotNull
        Long estoqueId,

        /** Preço a praticar; se ausente, usa o preço de venda do item. */
        @PositiveOrZero
        BigDecimal precoUnitario
) {
}
