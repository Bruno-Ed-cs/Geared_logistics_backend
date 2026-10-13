package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import java.math.BigDecimal;

public record ServicoResponse(
        Long id,
        String descricao,
        BigDecimal valorMaoDeObra
) {
}
