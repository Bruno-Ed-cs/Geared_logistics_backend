package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import java.time.Instant;

public record RastreamentoResponse(
        String codigoRastreio,
        String statusLocalizacao,
        Instant dataAtualizacao
) {
}
