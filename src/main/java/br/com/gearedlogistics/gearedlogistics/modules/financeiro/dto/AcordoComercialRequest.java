package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AcordoComercialRequest(
        @NotNull
        Long clienteId,

        Long ordemServicoId,

        @NotNull @PositiveOrZero
        BigDecimal valorTotal,

        @Size(max = 500)
        String condicoes,

        @NotNull @Positive
        Integer numeroParcelas,

        LocalDate dataRealizacao
) {
}
