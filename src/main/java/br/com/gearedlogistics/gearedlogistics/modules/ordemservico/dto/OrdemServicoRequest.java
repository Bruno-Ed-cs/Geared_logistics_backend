package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrdemServicoRequest(
        @NotNull
        Long clienteId,

        @NotNull
        Long veiculoId,

        @NotBlank @Size(max = 500)
        String descricaoProblema
) {
}
