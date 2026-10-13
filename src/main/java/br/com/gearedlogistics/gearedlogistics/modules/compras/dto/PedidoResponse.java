package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.StatusPedido;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record PedidoResponse(
        Long id,
        String numero,
        StatusPedido status,
        LocalDate dataSolicitacao,
        Long fornecedorId,
        String fornecedorNome,
        Long estoqueDestinoId,
        String estoqueDestinoNome,
        List<ItemPedidoResponse> itens,
        BigDecimal valorTotal,
        RastreamentoResponse rastreamento,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
