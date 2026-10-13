package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto;

import java.time.Instant;

public record FornecedorResponse(
        Long id,
        String nomeEmpresa,
        String cnpj,
        String email,
        String telefone,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
