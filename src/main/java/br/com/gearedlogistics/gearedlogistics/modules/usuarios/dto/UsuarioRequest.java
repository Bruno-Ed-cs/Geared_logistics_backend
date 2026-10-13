package br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto;

import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank @Size(max = 150)
        String nome,

        @NotBlank @Email @Size(max = 180)
        String email,

        @NotBlank @Size(min = 6, max = 100)
        String senha,

        @NotNull
        PerfilUsuario perfil,

        Boolean ativo
) {
}
