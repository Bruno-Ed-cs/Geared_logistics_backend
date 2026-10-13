package br.com.gearedlogistics.gearedlogistics.modules.compras.service;

import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.StatusPedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.AtualizarRastreamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PedidoService {

    PedidoResponse criar(PedidoRequest request);

    PedidoResponse buscarPorId(Long id);

    PageResponse<PedidoResponse> listar(Long fornecedorId, StatusPedido status, Pageable pageable);

    ItemPedidoResponse adicionarItem(Long id, ItemPedidoRequest request);

    void removerItem(Long id, Long itemId);

    PedidoResponse processar(Long id);

    PedidoResponse enviar(Long id);

    PedidoResponse entregar(Long id);

    PedidoResponse extraviar(Long id);

    PedidoResponse cancelar(Long id);

    RastreamentoResponse definirRastreamento(Long id, RastreamentoRequest request);

    RastreamentoResponse atualizarRastreamento(Long id, AtualizarRastreamentoRequest request);

    void excluir(Long id);
}
