package br.com.gearedlogistics.gearedlogistics.modules.clientes.service;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.dto.ClienteRequest;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.dto.ClienteResponse;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.mapper.ClienteMapper;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.repository.ClienteRepository;
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
public class ClienteServiceImpl implements ClienteService {

    private static final String RECURSO = "Cliente";

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponse criar(ClienteRequest request) {
        if (clienteRepository.existsByCpfCnpj(request.cpfCnpj())) {
            throw new BusinessException("Já existe um cliente com o CPF/CNPJ " + request.cpfCnpj());
        }
        Cliente cliente = clienteMapper.toEntity(request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        return clienteMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ClienteResponse> listar(String nome, String cpfCnpj, Pageable pageable) {
        Specification<Cliente> filtro = montarFiltro(nome, cpfCnpj);
        return PageResponse.from(clienteRepository.findAll(filtro, pageable).map(clienteMapper::toResponse));
    }

    @Override
    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);
        if (!cliente.getCpfCnpj().equals(request.cpfCnpj())
                && clienteRepository.existsByCpfCnpj(request.cpfCnpj())) {
            throw new BusinessException("Já existe um cliente com o CPF/CNPJ " + request.cpfCnpj());
        }
        clienteMapper.updateEntity(cliente, request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarEntidade(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private Specification<Cliente> montarFiltro(String nome, String cpfCnpj) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(nome)) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(cpfCnpj)) {
                predicates.add(cb.like(root.get("cpfCnpj"), "%" + cpfCnpj + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
