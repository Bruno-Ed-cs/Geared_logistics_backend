package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Cargo;
import br.com.gearedlogistics.gearedlogistics.shared.domain.Endereco;
import br.com.gearedlogistics.gearedlogistics.shared.validation.CpfCnpj;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioRequest(
        @NotBlank @Size(max = 150)
        String nome,

        @NotBlank @CpfCnpj
        String cpfCnpj,

        @Valid
        Endereco endereco,

        @NotBlank @Size(max = 30)
        String matricula,

        @NotNull
        Cargo cargo,

        @Size(max = 100)
        String especialidade,

        Integer nivelAcessoAdmin,

        @PositiveOrZero
        BigDecimal salario,

        LocalDate dataAdmissao,

        Boolean ativo,

        Long usuarioId
) {
}
