package br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;

import java.math.BigDecimal;
import java.time.Instant;

public record ItemResponse(
        Long id,
        String nome,
        String codigoFabricante,
        TipoItem tipo,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        BigDecimal margemLucro,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
