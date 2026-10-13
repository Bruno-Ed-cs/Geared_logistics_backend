package br.com.gearedlogistics.gearedlogistics.modules.compras;

import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.StatusPedido;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.AtualizarRastreamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.ItemPedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.PedidoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.compras.dto.RastreamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.compras.service.PedidoService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody PedidoRequest request) {
        return pedidoService.criar(request);
    }

    @GetMapping
    public PageResponse<PedidoResponse> listar(
            @RequestParam(required = false) Long fornecedorId,
            @RequestParam(required = false) StatusPedido status,
            @PageableDefault(size = 20) Pageable pageable) {
        return pedidoService.listar(fornecedorId, status, pageable);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping("/{id}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedidoResponse adicionarItem(@PathVariable Long id,
                                            @Valid @RequestBody ItemPedidoRequest request) {
        return pedidoService.adicionarItem(id, request);
    }

    @DeleteMapping("/{id}/itens/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerItem(@PathVariable Long id, @PathVariable Long itemId) {
        pedidoService.removerItem(id, itemId);
    }

    @PostMapping("/{id}/processar")
    public PedidoResponse processar(@PathVariable Long id) {
        return pedidoService.processar(id);
    }

    @PostMapping("/{id}/enviar")
    public PedidoResponse enviar(@PathVariable Long id) {
        return pedidoService.enviar(id);
    }

    @PostMapping("/{id}/entregar")
    public PedidoResponse entregar(@PathVariable Long id) {
        return pedidoService.entregar(id);
    }

    @PostMapping("/{id}/extraviar")
    public PedidoResponse extraviar(@PathVariable Long id) {
        return pedidoService.extraviar(id);
    }

    @PostMapping("/{id}/cancelar")
    public PedidoResponse cancelar(@PathVariable Long id) {
        return pedidoService.cancelar(id);
    }

    @PostMapping("/{id}/rastreamento")
    @ResponseStatus(HttpStatus.CREATED)
    public RastreamentoResponse definirRastreamento(@PathVariable Long id,
                                                    @Valid @RequestBody RastreamentoRequest request) {
        return pedidoService.definirRastreamento(id, request);
    }

    @PutMapping("/{id}/rastreamento")
    public RastreamentoResponse atualizarRastreamento(@PathVariable Long id,
                                                      @Valid @RequestBody AtualizarRastreamentoRequest request) {
        return pedidoService.atualizarRastreamento(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        pedidoService.excluir(id);
    }
}
