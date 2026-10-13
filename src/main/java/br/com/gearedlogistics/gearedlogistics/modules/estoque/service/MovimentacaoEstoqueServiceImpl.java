package br.com.gearedlogistics.gearedlogistics.modules.estoque.service;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.service.ItemService;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.MovimentacaoEstoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.PosicaoEstoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.mapper.MovimentacaoEstoqueMapper;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.repository.MovimentacaoEstoqueRepository;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.repository.PosicaoEstoqueRepository;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoEstoqueServiceImpl implements MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final PosicaoEstoqueRepository posicaoEstoqueRepository;
    private final MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;
    private final EstoqueService estoqueService;
    private final ItemService itemService;

    @Override
    @Transactional
    public MovimentacaoResponse registrar(MovimentacaoRequest request) {
        validarQuantidade(request);

        Estoque estoque = estoqueService.buscarEntidade(request.estoqueId());
        Item item = itemService.buscarEntidade(request.itemId());

        PosicaoEstoque posicao = posicaoEstoqueRepository
                .findByEstoqueIdAndItemId(estoque.getId(), item.getId())
                .orElseGet(() -> {
                    PosicaoEstoque nova = new PosicaoEstoque();
                    nova.setEstoque(estoque);
                    nova.setItem(item);
                    nova.setQuantidadeAtual(0);
                    return nova;
                });

        int saldoAtual = posicao.getQuantidadeAtual();
        int novoSaldo = calcularNovoSaldo(request.tipo(), saldoAtual, request.quantidade());
        posicao.setQuantidadeAtual(novoSaldo);
        posicaoEstoqueRepository.save(posicao);

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setEstoque(estoque);
        movimentacao.setItem(item);
        movimentacao.setTipo(request.tipo());
        movimentacao.setQuantidade(request.quantidade());
        movimentacao.setQuantidadeResultante(novoSaldo);
        movimentacao.setMotivo(request.motivo());

        return movimentacaoEstoqueMapper.toResponse(movimentacaoEstoqueRepository.save(movimentacao));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovimentacaoResponse> listar(Long itemId, Long estoqueId,
                                                     TipoMovimentacao tipo, Pageable pageable) {
        Specification<MovimentacaoEstoque> filtro = montarFiltro(itemId, estoqueId, tipo);
        return PageResponse.from(movimentacaoEstoqueRepository.findAll(filtro, pageable)
                .map(movimentacaoEstoqueMapper::toResponse));
    }

    private void validarQuantidade(MovimentacaoRequest request) {
        if (request.tipo() != TipoMovimentacao.AJUSTE && request.quantidade() <= 0) {
            throw new BusinessException("A quantidade deve ser maior que zero para " + request.tipo());
        }
    }

    private int calcularNovoSaldo(TipoMovimentacao tipo, int saldoAtual, int quantidade) {
        return switch (tipo) {
            case ENTRADA -> saldoAtual + quantidade;
            case SAIDA -> {
                if (saldoAtual < quantidade) {
                    throw new BusinessException(
                            "Estoque insuficiente: disponível " + saldoAtual + ", solicitado " + quantidade);
                }
                yield saldoAtual - quantidade;
            }
            case AJUSTE -> quantidade;
        };
    }

    private Specification<MovimentacaoEstoque> montarFiltro(Long itemId, Long estoqueId, TipoMovimentacao tipo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (itemId != null) {
                predicates.add(cb.equal(root.get("item").get("id"), itemId));
            }
            if (estoqueId != null) {
                predicates.add(cb.equal(root.get("estoque").get("id"), estoqueId));
            }
            if (tipo != null) {
                predicates.add(cb.equal(root.get("tipo"), tipo));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
