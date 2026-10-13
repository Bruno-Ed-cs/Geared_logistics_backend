package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoRequest(
        @NotNull
        Long clienteId,

        Long ordemServicoId,

        @NotNull @PositiveOrZero
        BigDecimal valorTotal,

        LocalDate dataRealizacao
) {
}
