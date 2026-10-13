package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegistrarPagamentoRequest(
        @NotNull @Positive
        BigDecimal valor
) {
}
