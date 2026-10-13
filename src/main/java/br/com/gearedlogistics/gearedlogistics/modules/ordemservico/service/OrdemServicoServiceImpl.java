package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.service;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.service.ItemService;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.service.ClienteService;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.service.EstoqueService;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.service.MovimentacaoEstoqueService;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.AplicacaoItem;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.OrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.Servico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.StatusOrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.mapper.OrdemServicoMapper;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.repository.OrdemServicoRepository;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.domain.Veiculo;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.service.VeiculoService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemServicoServiceImpl implements OrdemServicoService {

    private static final String RECURSO = "Ordem de serviço";

    private final OrdemServicoRepository ordemServicoRepository;
    private final OrdemServicoMapper ordemServicoMapper;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final ItemService itemService;
    private final EstoqueService estoqueService;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Override
    @Transactional
    public OrdemServicoResponse criar(OrdemServicoRequest request) {
        Cliente cliente = clienteService.buscarEntidade(request.clienteId());
        Veiculo veiculo = veiculoService.buscarEntidade(request.veiculoId());
        if (!veiculo.getCliente().getId().equals(cliente.getId())) {
            throw new BusinessException(
                    "O veículo " + veiculo.getPlaca() + " não pertence ao cliente informado");
        }

        OrdemServico ordem = new OrdemServico();
        ordem.setCliente(cliente);
        ordem.setVeiculo(veiculo);
        ordem.setDescricaoProblema(request.descricaoProblema());
        ordem.setStatus(StatusOrdemServico.ABERTA);
        return ordemServicoMapper.toResponse(ordemServicoRepository.save(ordem));
    }

    @Override
    @Transactional(readOnly = true)
    public OrdemServicoResponse buscarPorId(Long id) {
        return ordemServicoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrdemServicoResponse> listar(StatusOrdemServico status, Long clienteId, Long veiculoId,
                                                     Pageable pageable) {
        Specification<OrdemServico> filtro = montarFiltro(status, clienteId, veiculoId);
        return PageResponse.from(ordemServicoRepository.findAll(filtro, pageable)
                .map(ordemServicoMapper::toResponse));
    }

    @Override
    @Transactional
    public ServicoResponse adicionarServico(Long id, ServicoRequest request) {
        OrdemServico ordem = buscarEntidade(id);
        exigirEditavel(ordem);

        Servico servico = new Servico();
        servico.setDescricao(request.descricao());
        servico.setValorMaoDeObra(request.valorMaoDeObra());
        ordem.getServicos().add(servico);
        ordemServicoRepository.flush();

        return ordemServicoMapper.toServicoResponse(servico);
    }

    @Override
    @Transactional
    public AplicacaoItemResponse adicionarItem(Long id, AplicacaoItemRequest request) {
        OrdemServico ordem = buscarEntidade(id);
        exigirEditavel(ordem);

        Item item = itemService.buscarEntidade(request.itemId());
        Estoque estoque = estoqueService.buscarEntidade(request.estoqueId());
        BigDecimal preco = request.precoUnitario() != null
                ? request.precoUnitario()
                : (item.getPrecoVenda() != null ? item.getPrecoVenda() : BigDecimal.ZERO);

        AplicacaoItem aplicacao = new AplicacaoItem();
        aplicacao.setItem(item);
        aplicacao.setEstoque(estoque);
        aplicacao.setQuantidade(request.quantidade());
        aplicacao.setPrecoNoMomento(preco);
        ordem.getItensAplicados().add(aplicacao);

        // Baixa automática no estoque (lança 409 se não houver saldo)
        movimentacaoEstoqueService.registrar(new MovimentacaoRequest(
                estoque.getId(),
                item.getId(),
                TipoMovimentacao.SAIDA,
                request.quantidade(),
                "Aplicação na OS " + ordem.getId()));

        ordemServicoRepository.flush();
        return ordemServicoMapper.toAplicacaoItemResponse(aplicacao);
    }

    @Override
    @Transactional
    public OrdemServicoResponse iniciar(Long id) {
        OrdemServico ordem = buscarEntidade(id);
        if (ordem.getStatus() != StatusOrdemServico.ABERTA) {
            throw new BusinessException("Só é possível iniciar uma OS com status ABERTA");
        }
        ordem.setStatus(StatusOrdemServico.EM_ANDAMENTO);
        return ordemServicoMapper.toResponse(ordemServicoRepository.saveAndFlush(ordem));
    }

    @Override
    @Transactional
    public OrdemServicoResponse concluir(Long id) {
        OrdemServico ordem = buscarEntidade(id);
        if (ordem.getStatus() != StatusOrdemServico.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível concluir uma OS com status EM_ANDAMENTO");
        }
        ordem.setStatus(StatusOrdemServico.CONCLUIDA);
        ordem.setDataConclusao(Instant.now());
        return ordemServicoMapper.toResponse(ordemServicoRepository.saveAndFlush(ordem));
    }

    @Override
    @Transactional
    public OrdemServicoResponse cancelar(Long id) {
        OrdemServico ordem = buscarEntidade(id);
        if (ordem.getStatus() == StatusOrdemServico.CONCLUIDA
                || ordem.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new BusinessException("Não é possível cancelar uma OS com status " + ordem.getStatus());
        }
        ordem.setStatus(StatusOrdemServico.CANCELADA);
        estornarItens(ordem);
        return ordemServicoMapper.toResponse(ordemServicoRepository.saveAndFlush(ordem));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        OrdemServico ordem = buscarEntidade(id);
        if (ordem.getStatus() != StatusOrdemServico.ABERTA) {
            throw new BusinessException("Só é possível excluir uma OS com status ABERTA; use o cancelamento");
        }
        estornarItens(ordem);
        ordemServicoRepository.delete(ordem);
    }

    /**
     * Devolve ao estoque os itens que haviam sido debitados por esta OS.
     */
    private void estornarItens(OrdemServico ordem) {
        for (AplicacaoItem aplicacao : ordem.getItensAplicados()) {
            if (aplicacao.getEstoque() == null) {
                continue;
            }
            movimentacaoEstoqueService.registrar(new MovimentacaoRequest(
                    aplicacao.getEstoque().getId(),
                    aplicacao.getItem().getId(),
                    TipoMovimentacao.ENTRADA,
                    aplicacao.getQuantidade(),
                    "Estorno da OS " + ordem.getId()));
        }
    }

    private void exigirEditavel(OrdemServico ordem) {
        if (ordem.getStatus() == StatusOrdemServico.CONCLUIDA
                || ordem.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new BusinessException("Não é possível alterar uma OS com status " + ordem.getStatus());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrdemServico buscarEntidade(Long id) {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private Specification<OrdemServico> montarFiltro(StatusOrdemServico status, Long clienteId, Long veiculoId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (clienteId != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), clienteId));
            }
            if (veiculoId != null) {
                predicates.add(cb.equal(root.get("veiculo").get("id"), veiculoId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
