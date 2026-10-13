package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusNotaFiscal;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record NotaFiscalResponse(
        Long id,
        Long transacaoId,
        String numeroDocumento,
        BigDecimal impostos,
        LocalDate dataEmissao,
        StatusNotaFiscal status,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
