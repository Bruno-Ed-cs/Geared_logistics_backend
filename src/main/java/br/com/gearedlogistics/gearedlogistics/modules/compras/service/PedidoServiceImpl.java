package br.com.gearedlogistics.gearedlogistics.modules.compras.service;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.service.ItemService;
import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.ItemPedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.Pedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.Rastreamento;
import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.StatusPedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.AtualizarRastreamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.mapper.PedidoMapper;
import br.com.gearedlogistics.gearedlogistics.modules.compras.repository.PedidoRepository;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.service.EstoqueService;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.service.MovimentacaoEstoqueService;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain.Fornecedor;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.service.FornecedorService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private static final String RECURSO = "Pedido";

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final FornecedorService fornecedorService;
    private final EstoqueService estoqueService;
    private final ItemService itemService;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Override
    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        Fornecedor fornecedor = fornecedorService.buscarEntidade(request.fornecedorId());
        Estoque estoque = estoqueService.buscarEntidade(request.estoqueDestinoId());

        Pedido pedido = new Pedido();
        pedido.setFornecedor(fornecedor);
        pedido.setEstoqueDestino(estoque);
        pedido.setDataSolicitacao(request.dataSolicitacao() != null ? request.dataSolicitacao() : LocalDate.now());
        pedido.setStatus(StatusPedido.SOLICITADO);
        return pedidoMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        return pedidoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PedidoResponse> listar(Long fornecedorId, StatusPedido status, Pageable pageable) {
        Specification<Pedido> filtro = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (fornecedorId != null) {
                predicates.add(cb.equal(root.get("fornecedor").get("id"), fornecedorId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return PageResponse.from(pedidoRepository.findAll(filtro, pageable).map(pedidoMapper::toResponse));
    }

    @Override
    @Transactional
    public ItemPedidoResponse adicionarItem(Long id, ItemPedidoRequest request) {
        Pedido pedido = buscarEntidade(id);
        exigirStatus(pedido, StatusPedido.SOLICITADO, "adicionar itens");

        Item item = itemService.buscarEntidade(request.itemId());
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setItem(item);
        itemPedido.setQuantidadeSolicitada(request.quantidadeSolicitada());
        itemPedido.setPrecoNegociado(request.precoNegociado());
        pedido.getItens().add(itemPedido);
        pedidoRepository.flush();

        return pedidoMapper.toItemResponse(itemPedido);
    }

    @Override
    @Transactional
    public void removerItem(Long id, Long itemId) {
        Pedido pedido = buscarEntidade(id);
        exigirStatus(pedido, StatusPedido.SOLICITADO, "remover itens");

        boolean removido = pedido.getItens().removeIf(item -> item.getId().equals(itemId));
        if (!removido) {
            throw new ResourceNotFoundException("Item do pedido", itemId);
        }
        pedidoRepository.flush();
    }

    @Override
    @Transactional
    public PedidoResponse processar(Long id) {
        return transicionar(id, StatusPedido.SOLICITADO, StatusPedido.EM_PROCESSAMENTO, "processar");
    }

    @Override
    @Transactional
    public PedidoResponse enviar(Long id) {
        return transicionar(id, StatusPedido.EM_PROCESSAMENTO, StatusPedido.ENVIADO, "enviar");
    }

    @Override
    @Transactional
    public PedidoResponse entregar(Long id) {
        Pedido pedido = buscarEntidade(id);
        exigirStatus(pedido, StatusPedido.ENVIADO, "entregar");
        if (pedido.getItens().isEmpty()) {
            throw new BusinessException("Não é possível entregar um pedido sem itens");
        }

        // Dá entrada no estoque de destino para cada item do pedido.
        for (ItemPedido item : pedido.getItens()) {
            movimentacaoEstoqueService.registrar(new MovimentacaoRequest(
                    pedido.getEstoqueDestino().getId(),
                    item.getItem().getId(),
                    TipoMovimentacao.ENTRADA,
                    item.getQuantidadeSolicitada(),
                    "Recebimento do pedido " + numero(pedido.getId())));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        return pedidoMapper.toResponse(pedidoRepository.saveAndFlush(pedido));
    }

    @Override
    @Transactional
    public PedidoResponse extraviar(Long id) {
        return transicionar(id, StatusPedido.ENVIADO, StatusPedido.EXTRAVIADO, "extraviar");
    }

    @Override
    @Transactional
    public PedidoResponse cancelar(Long id) {
        Pedido pedido = buscarEntidade(id);
        if (pedido.getStatus() != StatusPedido.SOLICITADO
                && pedido.getStatus() != StatusPedido.EM_PROCESSAMENTO) {
            throw new BusinessException("Não é possível cancelar um pedido com status " + pedido.getStatus());
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoMapper.toResponse(pedidoRepository.saveAndFlush(pedido));
    }

    @Override
    @Transactional
    public RastreamentoResponse definirRastreamento(Long id, RastreamentoRequest request) {
        Pedido pedido = buscarEntidade(id);
        if (pedido.getRastreamento() != null) {
            throw new BusinessException("O pedido já possui rastreamento");
        }
        if (pedido.getStatus() != StatusPedido.EM_PROCESSAMENTO && pedido.getStatus() != StatusPedido.ENVIADO) {
            throw new BusinessException(
                    "Não é possível rastrear um pedido com status " + pedido.getStatus());
        }

        Rastreamento rastreamento = new Rastreamento();
        rastreamento.setPedido(pedido);
        rastreamento.setCodigoRastreio(request.codigoRastreio());
        rastreamento.setStatusLocalizacao(request.statusLocalizacao());
        rastreamento.setDataAtualizacao(Instant.now());
        pedido.setRastreamento(rastreamento);

        pedidoRepository.saveAndFlush(pedido);
        return pedidoMapper.toRastreamentoResponse(rastreamento);
    }

    @Override
    @Transactional
    public RastreamentoResponse atualizarRastreamento(Long id, AtualizarRastreamentoRequest request) {
        Pedido pedido = buscarEntidade(id);
        Rastreamento rastreamento = pedido.getRastreamento();
        if (rastreamento == null) {
            throw new ResourceNotFoundException("Rastreamento do pedido", id);
        }
        rastreamento.setStatusLocalizacao(request.statusLocalizacao());
        rastreamento.setDataAtualizacao(Instant.now());
        pedidoRepository.saveAndFlush(pedido);
        return pedidoMapper.toRastreamentoResponse(rastreamento);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Pedido pedido = buscarEntidade(id);
        exigirStatus(pedido, StatusPedido.SOLICITADO, "excluir");
        pedidoRepository.delete(pedido);
    }

    private PedidoResponse transicionar(Long id, StatusPedido de, StatusPedido para, String acao) {
        Pedido pedido = buscarEntidade(id);
        exigirStatus(pedido, de, acao);
        pedido.setStatus(para);
        return pedidoMapper.toResponse(pedidoRepository.saveAndFlush(pedido));
    }

    private void exigirStatus(Pedido pedido, StatusPedido esperado, String acao) {
        if (pedido.getStatus() != esperado) {
            throw new BusinessException(
                    "Não é possível " + acao + " um pedido com status " + pedido.getStatus());
        }
    }

    private Pedido buscarEntidade(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private String numero(Long id) {
        return id == null ? null : String.format("PED-%06d", id);
    }
}
