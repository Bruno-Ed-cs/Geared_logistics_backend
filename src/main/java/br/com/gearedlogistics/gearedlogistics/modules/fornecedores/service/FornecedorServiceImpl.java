package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.service;

import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain.Fornecedor;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorRequest;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorResponse;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.mapper.FornecedorMapper;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.repository.FornecedorRepository;
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

@Service
@RequiredArgsConstructor
public class FornecedorServiceImpl implements FornecedorService {

    private static final String RECURSO = "Fornecedor";

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    @Override
    @Transactional
    public FornecedorResponse criar(FornecedorRequest request) {
        if (fornecedorRepository.existsByCnpj(request.cnpj())) {
            throw new BusinessException("Já existe um fornecedor com o CNPJ " + request.cnpj());
        }
        Fornecedor fornecedor = fornecedorMapper.toEntity(request);
        return fornecedorMapper.toResponse(fornecedorRepository.save(fornecedor));
    }

    @Override
    @Transactional(readOnly = true)
    public FornecedorResponse buscarPorId(Long id) {
        return fornecedorMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FornecedorResponse> listar(String nome, String cnpj, Pageable pageable) {
        Specification<Fornecedor> filtro = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(nome)) {
                predicates.add(cb.like(cb.lower(root.get("nomeEmpresa")), "%" + nome.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(cnpj)) {
                predicates.add(cb.like(root.get("cnpj"), "%" + cnpj + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(fornecedorRepository.findAll(filtro, pageable).map(fornecedorMapper::toResponse));
    }

    @Override
    @Transactional
    public FornecedorResponse atualizar(Long id, FornecedorRequest request) {
        Fornecedor fornecedor = buscarEntidade(id);
        if (!fornecedor.getCnpj().equals(request.cnpj()) && fornecedorRepository.existsByCnpj(request.cnpj())) {
            throw new BusinessException("Já existe um fornecedor com o CNPJ " + request.cnpj());
        }
        fornecedorMapper.updateEntity(fornecedor, request);
        return fornecedorMapper.toResponse(fornecedorRepository.save(fornecedor));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        fornecedorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Fornecedor buscarEntidade(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }
}
