package br.com.gearedlogistics.gearedlogistics.modules.usuarios.service;

import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.Usuario;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto.UsuarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.dto.UsuarioResponse;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.mapper.UsuarioMapper;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.repository.UsuarioRepository;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private static final String RECURSO = "Usuário";

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário com o e-mail " + request.email());
        }
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return usuarioMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UsuarioResponse> listar(Pageable pageable) {
        return PageResponse.from(usuarioRepository.findAll(pageable).map(usuarioMapper::toResponse));
    }

    @Override
    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = buscarEntidade(id);
        if (!usuario.getEmail().equals(request.email()) && usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Já existe um usuário com o e-mail " + request.email());
        }
        usuarioMapper.updateEntity(usuario, request);
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }
}
