package br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto;

import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.PerfilUsuario;

import java.time.Instant;

/**
 * Dados públicos de um usuário. A senha nunca é exposta.
 */
public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        PerfilUsuario perfil,
        boolean ativo,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
