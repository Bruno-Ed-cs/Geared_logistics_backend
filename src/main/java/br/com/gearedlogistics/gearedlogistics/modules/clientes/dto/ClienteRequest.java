package br.com.gearedlogistics.gearedlogistics.modules.clientes.dto;

import br.com.gearedlogistics.gearedlogistics.shared.domain.Endereco;
import br.com.gearedlogistics.gearedlogistics.shared.validation.CpfCnpj;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ClienteRequest(
        @NotBlank @Size(max = 150)
        String nome,

        @NotBlank @CpfCnpj
        String cpfCnpj,

        @Email @Size(max = 180)
        String email,

        @Size(max = 100)
        String profissao,

        @Size(max = 255)
        String informacaoContato,

        @Valid
        Endereco endereco,

        Set<@Size(max = 20) String> telefones
) {
}
