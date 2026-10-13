package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.service;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Cargo;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Funcionario;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioResponse;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.mapper.FuncionarioMapper;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.repository.FuncionarioRepository;
import br.com.gearedlogistics.gearedlogistics.modules.usuarios.service.UsuarioService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final String RECURSO = "Funcionário";

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final UsuarioService usuarioService;

    @Override
    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest request) {
        if (funcionarioRepository.existsByMatricula(request.matricula())) {
            throw new BusinessException("Já existe um funcionário com a matrícula " + request.matricula());
        }
        Funcionario funcionario = funcionarioMapper.toEntity(request);
        aplicarUsuario(funcionario, request.usuarioId(), null);
        return funcionarioMapper.toResponse(funcionarioRepository.save(funcionario));
    }

    @Override
    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(Long id) {
        return funcionarioMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FuncionarioResponse> listar(String nome, Cargo cargo, Pageable pageable) {
        Specification<Funcionario> filtro = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(nome)) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if (cargo != null) {
                predicates.add(cb.equal(root.get("cargo"), cargo));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(funcionarioRepository.findAll(filtro, pageable)
                .map(funcionarioMapper::toResponse));
    }

    @Override
    @Transactional
    public FuncionarioResponse atualizar(Long id, FuncionarioRequest request) {
        Funcionario funcionario = buscarEntidade(id);
        if (!funcionario.getMatricula().equals(request.matricula())
                && funcionarioRepository.existsByMatricula(request.matricula())) {
            throw new BusinessException("Já existe um funcionário com a matrícula " + request.matricula());
        }
        Long usuarioAtualId = funcionario.getUsuario() != null ? funcionario.getUsuario().getId() : null;
        funcionarioMapper.updateEntity(funcionario, request);
        aplicarUsuario(funcionario, request.usuarioId(), usuarioAtualId);
        return funcionarioMapper.toResponse(funcionarioRepository.save(funcionario));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        funcionarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Funcionario buscarEntidade(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private void aplicarUsuario(Funcionario funcionario, Long usuarioId, Long usuarioAtualId) {
        if (usuarioId == null) {
            funcionario.setUsuario(null);
            return;
        }
        if (!Objects.equals(usuarioId, usuarioAtualId) && funcionarioRepository.existsByUsuarioId(usuarioId)) {
            throw new BusinessException("O usuário informado já está vinculado a outro funcionário");
        }
        // Lança 404 se o usuário não existir.
        funcionario.setUsuario(usuarioService.buscarEntidade(usuarioId));
    }
}
