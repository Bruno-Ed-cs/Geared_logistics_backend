package br.com.gearedlogistics.gearedlogistics.modules.estoque.service;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.PosicaoEstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.mapper.EstoqueMapper;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.mapper.PosicaoEstoqueMapper;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.repository.EstoqueRepository;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.repository.PosicaoEstoqueRepository;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
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

@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

    private static final String RECURSO = "Estoque";

    private final EstoqueRepository estoqueRepository;
    private final PosicaoEstoqueRepository posicaoEstoqueRepository;
    private final EstoqueMapper estoqueMapper;
    private final PosicaoEstoqueMapper posicaoEstoqueMapper;

    @Override
    @Transactional
    public EstoqueResponse criar(EstoqueRequest request) {
        Estoque estoque = estoqueMapper.toEntity(request);
        return estoqueMapper.toResponse(estoqueRepository.save(estoque));
    }

    @Override
    @Transactional(readOnly = true)
    public EstoqueResponse buscarPorId(Long id) {
        return estoqueMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EstoqueResponse> listar(String nome, Pageable pageable) {
        Specification<Estoque> filtro = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(nome)) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(estoqueRepository.findAll(filtro, pageable).map(estoqueMapper::toResponse));
    }

    @Override
    @Transactional
    public EstoqueResponse atualizar(Long id, EstoqueRequest request) {
        Estoque estoque = buscarEntidade(id);
        estoqueMapper.updateEntity(estoque, request);
        return estoqueMapper.toResponse(estoqueRepository.save(estoque));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        estoqueRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosicaoEstoqueResponse> listarPosicoes(Long estoqueId) {
        buscarEntidade(estoqueId);
        return posicaoEstoqueRepository.findByEstoqueId(estoqueId).stream()
                .map(posicaoEstoqueMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Estoque buscarEntidade(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }
}
