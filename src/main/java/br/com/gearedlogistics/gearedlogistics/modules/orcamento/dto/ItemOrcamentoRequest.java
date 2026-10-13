package br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.TipoItemOrcamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ItemOrcamentoRequest(
        @NotBlank @Size(max = 255)
        String descricao,

        @NotNull
        TipoItemOrcamento tipo,

        @NotNull @Positive
        Integer quantidade,

        @NotNull @PositiveOrZero
        BigDecimal valorUnitario,

        /** Opcional: vincula a linha a um item do catálogo. */
        Long itemId
) {
}
