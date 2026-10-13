package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PedidoRequest(
        @NotNull
        Long fornecedorId,

        @NotNull
        Long estoqueDestinoId,

        LocalDate dataSolicitacao
) {
}
