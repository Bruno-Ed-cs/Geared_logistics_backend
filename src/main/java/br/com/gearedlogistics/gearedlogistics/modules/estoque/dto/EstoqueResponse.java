package br.com.gearedlogistics.gearedlogistics.modules.estoque.dto;

import java.time.Instant;

public record EstoqueResponse(
        Long id,
        String nome,
        String localizacao,
        Integer capacidade,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
