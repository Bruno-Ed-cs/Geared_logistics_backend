package br.com.gearedlogistics.gearedlogistics.modules.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record EstoqueRequest(
        @NotBlank @Size(max = 100)
        String nome,

        @Size(max = 150)
        String localizacao,

        @PositiveOrZero
        Integer capacidade
) {
}
