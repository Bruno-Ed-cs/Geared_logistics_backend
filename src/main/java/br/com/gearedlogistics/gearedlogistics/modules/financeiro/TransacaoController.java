package br.com.gearedlogistics.gearedlogistics.modules.financeiro;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TipoTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.AcordoComercialRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.PagamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.RegistrarPagamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.TransacaoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.VendaRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.service.TransacaoComercialService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoComercialService transacaoService;

    @PostMapping("/vendas")
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse criarVenda(@Valid @RequestBody VendaRequest request) {
        return transacaoService.criarVenda(request);
    }

    @PostMapping("/pagamentos")
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse criarPagamento(@Valid @RequestBody PagamentoRequest request) {
        return transacaoService.criarPagamento(request);
    }

    @PostMapping("/acordos")
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse criarAcordo(@Valid @RequestBody AcordoComercialRequest request) {
        return transacaoService.criarAcordo(request);
    }

    @GetMapping
    public PageResponse<TransacaoResponse> listar(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long ordemServicoId,
            @RequestParam(required = false) StatusTransacao status,
            @RequestParam(required = false) TipoTransacao tipo,
            @PageableDefault(size = 20) Pageable pageable) {
        return transacaoService.listar(clienteId, ordemServicoId, status, tipo, pageable);
    }

    @GetMapping("/{id}")
    public TransacaoResponse buscar(@PathVariable Long id) {
        return transacaoService.buscarPorId(id);
    }

    @PostMapping("/{id}/aprovar")
    public TransacaoResponse aprovar(@PathVariable Long id) {
        return transacaoService.aprovar(id);
    }

    @PostMapping("/{id}/pagar")
    public TransacaoResponse pagar(@PathVariable Long id) {
        return transacaoService.pagar(id);
    }

    @PostMapping("/{id}/cancelar")
    public TransacaoResponse cancelar(@PathVariable Long id) {
        return transacaoService.cancelar(id);
    }

    @PostMapping("/{id}/pagamentos")
    public TransacaoResponse registrarPagamento(@PathVariable Long id,
                                                @Valid @RequestBody RegistrarPagamentoRequest request) {
        return transacaoService.registrarPagamento(id, request);
    }

    @PostMapping("/{id}/inadimplencia")
    public TransacaoResponse marcarInadimplente(@PathVariable Long id) {
        return transacaoService.marcarInadimplente(id);
    }

    @PostMapping("/{id}/regularizar")
    public TransacaoResponse regularizar(@PathVariable Long id) {
        return transacaoService.regularizar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        transacaoService.excluir(id);
    }
}
