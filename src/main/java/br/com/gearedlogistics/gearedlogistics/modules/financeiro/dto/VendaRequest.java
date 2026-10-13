package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VendaRequest(
        @NotNull
        Long clienteId,

        Long ordemServicoId,

        @NotNull @PositiveOrZero
        BigDecimal valorTotal,

        @NotBlank @Size(max = 30)
        String formaPagamento,

        LocalDate dataRealizacao
) {
}
