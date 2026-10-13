package br.com.gearedlogistics.gearedlogistics.modules.usuarios.service;

import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.Usuario;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto.UsuarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto.UsuarioResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    UsuarioResponse criar(UsuarioRequest request);

    UsuarioResponse buscarPorId(Long id);

    PageResponse<UsuarioResponse> listar(Pageable pageable);

    UsuarioResponse atualizar(Long id, UsuarioRequest request);

    void excluir(Long id);

    Usuario buscarEntidade(Long id);
}
