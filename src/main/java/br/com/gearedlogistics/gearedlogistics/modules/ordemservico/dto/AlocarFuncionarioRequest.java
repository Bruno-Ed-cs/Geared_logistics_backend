package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import jakarta.validation.constraints.NotNull;

public record AlocarFuncionarioRequest(
        @NotNull
        Long funcionarioId
) {
}
