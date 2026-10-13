package br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record OrcamentoRequest(
        @NotNull
        Long ordemServicoId,

        LocalDate dataValidade,

        @Size(max = 500)
        String observacoes
) {
}
