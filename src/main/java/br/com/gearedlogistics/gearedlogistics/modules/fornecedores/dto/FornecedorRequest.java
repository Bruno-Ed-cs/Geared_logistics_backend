package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto;

import br.com.gearedlogistics.gearedlogistics.shared.validation.CpfCnpj;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FornecedorRequest(
        @NotBlank @Size(max = 150)
        String nomeEmpresa,

        @NotBlank @CpfCnpj
        String cnpj,

        @Email @Size(max = 180)
        String email,

        @Size(max = 20)
        String telefone
) {
}
