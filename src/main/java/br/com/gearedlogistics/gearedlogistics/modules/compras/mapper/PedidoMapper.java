package br.com.gearedlogistics.gearedlogistics.modules.compras.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.ItemPedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.Pedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.Rastreamento;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {
        List<ItemPedidoResponse> itens = pedido.getItens().stream()
                .map(this::toItemResponse)
                .toList();

        BigDecimal valorTotal = pedido.getItens().stream()
                .map(this::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PedidoResponse(
                pedido.getId(),
                numero(pedido.getId()),
                pedido.getStatus(),
                pedido.getDataSolicitacao(),
                pedido.getFornecedor().getId(),
                pedido.getFornecedor().getNomeEmpresa(),
                pedido.getEstoqueDestino().getId(),
                pedido.getEstoqueDestino().getNome(),
                itens,
                valorTotal,
                toRastreamentoResponse(pedido.getRastreamento()),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm());
    }

    public ItemPedidoResponse toItemResponse(ItemPedido item) {
        return new ItemPedidoResponse(
                item.getId(),
                item.getItem().getId(),
                item.getItem().getNome(),
                item.getItem().getTipo(),
                item.getQuantidadeSolicitada(),
                item.getPrecoNegociado(),
                subtotal(item));
    }

    public RastreamentoResponse toRastreamentoResponse(Rastreamento rastreamento) {
        if (rastreamento == null) {
            return null;
        }
        return new RastreamentoResponse(
                rastreamento.getCodigoRastreio(),
                rastreamento.getStatusLocalizacao(),
                rastreamento.getDataAtualizacao());
    }

    private BigDecimal subtotal(ItemPedido item) {
        return item.getPrecoNegociado().multiply(BigDecimal.valueOf(item.getQuantidadeSolicitada()));
    }

    private String numero(Long id) {
        return id == null ? null : String.format("PED-%06d", id);
    }
}
