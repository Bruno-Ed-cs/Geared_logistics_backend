package br.com.gearedlogistics.gearedlogistics.modules.compras.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RastreamentoRequest(
        @NotBlank @Size(max = 60)
        String codigoRastreio,

        @Size(max = 255)
        String statusLocalizacao
) {
}
