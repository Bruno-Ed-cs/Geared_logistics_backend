package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NotaFiscalRequest(
        @NotNull
        Long transacaoId,

        @NotBlank @Size(max = 60)
        String numeroDocumento,

        @PositiveOrZero
        BigDecimal impostos,

        LocalDate dataEmissao
) {
}
