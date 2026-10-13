package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import jakarta.validation.constraints.Size;

public record AtualizarRastreamentoRequest(
        @Size(max = 255)
        String statusLocalizacao
) {
}
