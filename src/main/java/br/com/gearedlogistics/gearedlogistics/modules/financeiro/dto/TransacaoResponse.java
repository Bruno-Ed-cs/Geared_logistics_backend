package br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusPagamento;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TipoTransacao;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Resposta única para as três subclasses de transação; os campos específicos
 * de um tipo vêm nulos nos demais.
 */
public record TransacaoResponse(
        Long id,
        TipoTransacao tipo,
        StatusTransacao status,
        BigDecimal valorTotal,
        LocalDate dataRealizacao,
        Long clienteId,
        String clienteNome,
        Long ordemServicoId,
        String ordemServicoNumero,
        // Venda
        String formaPagamento,
        // Pagamento
        BigDecimal valorPago,
        StatusPagamento statusPagamento,
        LocalDate dataQuitacao,
        // Acordo
        String condicoes,
        Integer numeroParcelas,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
