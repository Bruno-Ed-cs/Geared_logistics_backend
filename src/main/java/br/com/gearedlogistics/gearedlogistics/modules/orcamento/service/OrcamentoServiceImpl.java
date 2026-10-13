package br.com.gearedlogistics.gearedlogistics.modules.orcamento.service;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.service.ItemService;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.ItemOrcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.Orcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.StatusOrcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.mapper.OrcamentoMapper;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.repository.OrcamentoRepository;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.OrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.StatusOrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.service.OrdemServicoService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrcamentoServiceImpl implements OrcamentoService {

    private static final String RECURSO = "Orçamento";

    private final OrcamentoRepository orcamentoRepository;
    private final OrcamentoMapper orcamentoMapper;
    private final OrdemServicoService ordemServicoService;
    private final ItemService itemService;

    @Override
    @Transactional
    public OrcamentoResponse criar(OrcamentoRequest request) {
        OrdemServico ordem = ordemServicoService.buscarEntidade(request.ordemServicoId());
        if (ordem.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new BusinessException("Não é possível orçar uma ordem de serviço cancelada");
        }

        Orcamento orcamento = new Orcamento();
        orcamento.setOrdemServico(ordem);
        orcamento.setObservacoes(request.observacoes());
        orcamento.setDataValidade(request.dataValidade());
        orcamento.setStatus(StatusOrcamento.RASCUNHO);
        return orcamentoMapper.toResponse(orcamentoRepository.save(orcamento));
    }

    @Override
    @Transactional(readOnly = true)
    public OrcamentoResponse buscarPorId(Long id) {
        return orcamentoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrcamentoResponse> listar(Long ordemServicoId, StatusOrcamento status, Pageable pageable) {
        Specification<Orcamento> filtro = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ordemServicoId != null) {
                predicates.add(cb.equal(root.get("ordemServico").get("id"), ordemServicoId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(orcamentoRepository.findAll(filtro, pageable).map(orcamentoMapper::toResponse));
    }

    @Override
    @Transactional
    public ItemOrcamentoResponse adicionarItem(Long id, ItemOrcamentoRequest request) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.RASCUNHO, "adicionar itens");

        ItemOrcamento item = new ItemOrcamento();
        item.setDescricao(request.descricao());
        item.setTipo(request.tipo());
        item.setQuantidade(request.quantidade());
        item.setValorUnitario(request.valorUnitario());
        if (request.itemId() != null) {
            item.setItem(itemService.buscarEntidade(request.itemId()));
        }
        orcamento.getItens().add(item);
        orcamentoRepository.flush();

        return orcamentoMapper.toItemResponse(item);
    }

    @Override
    @Transactional
    public void removerItem(Long id, Long itemId) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.RASCUNHO, "remover itens");

        boolean removido = orcamento.getItens().removeIf(item -> item.getId().equals(itemId));
        if (!removido) {
            throw new ResourceNotFoundException("Item do orçamento", itemId);
        }
        orcamentoRepository.flush();
    }

    @Override
    @Transactional
    public OrcamentoResponse enviar(Long id) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.RASCUNHO, "enviar");
        if (orcamento.getItens().isEmpty()) {
            throw new BusinessException("Não é possível enviar um orçamento sem itens");
        }
        orcamento.setStatus(StatusOrcamento.ENVIADO);
        return orcamentoMapper.toResponse(orcamentoRepository.saveAndFlush(orcamento));
    }

    @Override
    @Transactional
    public OrcamentoResponse aprovar(Long id) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.ENVIADO, "aprovar");
        orcamento.setStatus(StatusOrcamento.APROVADO);
        return orcamentoMapper.toResponse(orcamentoRepository.saveAndFlush(orcamento));
    }

    @Override
    @Transactional
    public OrcamentoResponse rejeitar(Long id) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.ENVIADO, "rejeitar");
        orcamento.setStatus(StatusOrcamento.REJEITADO);
        return orcamentoMapper.toResponse(orcamentoRepository.saveAndFlush(orcamento));
    }

    @Override
    @Transactional
    public OrcamentoResponse cancelar(Long id) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.RASCUNHO, "cancelar");
        orcamento.setStatus(StatusOrcamento.CANCELADO);
        return orcamentoMapper.toResponse(orcamentoRepository.saveAndFlush(orcamento));
    }

    @Override
    @Transactional
    public OrcamentoResponse expirar(Long id) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.ENVIADO, "expirar");
        if (orcamento.getDataValidade() == null || !orcamento.getDataValidade().isBefore(LocalDate.now())) {
            throw new BusinessException("O orçamento ainda não venceu");
        }
        orcamento.setStatus(StatusOrcamento.EXPIRADO);
        return orcamentoMapper.toResponse(orcamentoRepository.saveAndFlush(orcamento));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Orcamento orcamento = buscarEntidade(id);
        exigirStatus(orcamento, StatusOrcamento.RASCUNHO, "excluir");
        orcamentoRepository.delete(orcamento);
    }

    private void exigirStatus(Orcamento orcamento, StatusOrcamento esperado, String acao) {
        if (orcamento.getStatus() != esperado) {
            throw new BusinessException(
                    "Não é possível " + acao + " um orçamento com status " + orcamento.getStatus());
        }
    }

    private Orcamento buscarEntidade(Long id) {
        return orcamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }
}
