package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Cargo;
import br.com.gearedlogistics.gearedlogistics.shared.domain.Endereco;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record FuncionarioResponse(
        Long id,
        String nome,
        String cpfCnpj,
        Endereco endereco,
        String matricula,
        Cargo cargo,
        String especialidade,
        Integer nivelAcessoAdmin,
        BigDecimal salario,
        LocalDate dataAdmissao,
        boolean ativo,
        Long usuarioId,
        String usuarioEmail,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
