package br.com.gearedlogistics.gearedlogistics.modules.clientes.dto;

import br.com.gearedlogistics.gearedlogistics.shared.domain.Endereco;

import java.time.Instant;
import java.util.Set;

public record ClienteResponse(
        Long id,
        String nome,
        String cpfCnpj,
        String email,
        String profissao,
        String informacaoContato,
        Endereco endereco,
        Set<String> telefones,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
