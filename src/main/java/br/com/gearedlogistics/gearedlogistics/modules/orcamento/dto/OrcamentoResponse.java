package br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.StatusOrcamento;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record OrcamentoResponse(
        Long id,
        Long ordemServicoId,
        String ordemServicoNumero,
        StatusOrcamento status,
        LocalDate dataValidade,
        boolean vencido,
        String observacoes,
        List<ItemOrcamentoResponse> itens,
        BigDecimal valorTotal,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
