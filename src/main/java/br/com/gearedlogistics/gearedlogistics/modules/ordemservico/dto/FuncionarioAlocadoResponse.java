package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Cargo;

public record FuncionarioAlocadoResponse(
        Long id,
        String nome,
        Cargo cargo
) {
}
