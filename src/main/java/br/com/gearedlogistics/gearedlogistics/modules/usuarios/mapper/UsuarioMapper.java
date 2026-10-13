package br.com.gearedlogistics.gearedlogistics.modules.usuarios.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.Usuario;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto.UsuarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto.UsuarioResponse;
import br.com.gearedlogistics.gearedlogistics.shared.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements BaseMapper<UsuarioRequest, UsuarioResponse, Usuario> {

    @Override
    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        aplicarDados(usuario, request);
        return usuario;
    }

    @Override
    public UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getPerfil(),
                entity.isAtivo(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    @Override
    public void updateEntity(Usuario entity, UsuarioRequest request) {
        aplicarDados(entity, request);
    }

    private void aplicarDados(Usuario usuario, UsuarioRequest request) {
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());
        usuario.setPerfil(request.perfil());
        usuario.setAtivo(request.ativo() == null || request.ativo());
    }
}
