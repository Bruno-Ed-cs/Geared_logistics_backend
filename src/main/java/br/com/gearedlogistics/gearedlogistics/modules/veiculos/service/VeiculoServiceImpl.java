package br.com.gearedlogistics.gearedlogistics.modules.veiculos.service;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.service.ClienteService;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.domain.Veiculo;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.mapper.VeiculoMapper;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.repository.VeiculoRepository;
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
public class VeiculoServiceImpl implements VeiculoService {

    private static final String RECURSO = "Veículo";

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper veiculoMapper;
    private final ClienteService clienteService;

    @Override
    @Transactional
    public VeiculoResponse criar(VeiculoRequest request) {
        if (veiculoRepository.existsByPlaca(request.placa())) {
            throw new BusinessException("Já existe um veículo com a placa " + request.placa());
        }
        Cliente cliente = clienteService.buscarEntidade(request.clienteId());
        Veiculo veiculo = veiculoMapper.toEntity(request, cliente);
        return veiculoMapper.toResponse(veiculoRepository.save(veiculo));
    }

    @Override
    @Transactional(readOnly = true)
    public VeiculoResponse buscarPorId(Long id) {
        return veiculoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<VeiculoResponse> listar(String placa, Long clienteId, Pageable pageable) {
        Specification<Veiculo> filtro = montarFiltro(placa, clienteId);
        return PageResponse.from(veiculoRepository.findAll(filtro, pageable).map(veiculoMapper::toResponse));
    }

    @Override
    @Transactional
    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        Veiculo veiculo = buscarEntidade(id);
        if (!veiculo.getPlaca().equals(request.placa()) && veiculoRepository.existsByPlaca(request.placa())) {
            throw new BusinessException("Já existe um veículo com a placa " + request.placa());
        }
        Cliente cliente = clienteService.buscarEntidade(request.clienteId());
        veiculoMapper.updateEntity(veiculo, request, cliente);
        return veiculoMapper.toResponse(veiculoRepository.save(veiculo));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        veiculoRepository.deleteById(id);
    }

    private Veiculo buscarEntidade(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private Specification<Veiculo> montarFiltro(String placa, Long clienteId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(placa)) {
                predicates.add(cb.like(cb.upper(root.get("placa")), "%" + placa.toUpperCase() + "%"));
            }
            if (clienteId != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), clienteId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
