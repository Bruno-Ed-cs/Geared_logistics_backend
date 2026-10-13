package br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ItemRequest(
        @NotBlank @Size(max = 150)
        String nome,

        @Size(max = 60)
        String codigoFabricante,

        @NotNull
        TipoItem tipo,

        @PositiveOrZero
        BigDecimal precoCusto,

        @PositiveOrZero
        BigDecimal precoVenda
) {
}
