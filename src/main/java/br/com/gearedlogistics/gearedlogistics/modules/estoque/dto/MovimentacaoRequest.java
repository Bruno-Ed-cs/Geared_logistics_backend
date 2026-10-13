package br.com.gearedlogistics.gearedlogistics.modules.estoque.dto;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MovimentacaoRequest(
        @NotNull
        Long estoqueId,

        @NotNull
        Long itemId,

        @NotNull
        TipoMovimentacao tipo,

        @NotNull @PositiveOrZero
        Integer quantidade,

        @Size(max = 255)
        String motivo
) {
}
