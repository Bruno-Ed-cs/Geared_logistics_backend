package br.com.gearedlogistics.gearedlogistics.modules.orcamento;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.StatusOrcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.service.OrcamentoService;
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
@RequestMapping("/api/orcamentos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrcamentoResponse criar(@Valid @RequestBody OrcamentoRequest request) {
        return orcamentoService.criar(request);
    }

    @GetMapping
    public PageResponse<OrcamentoResponse> listar(
            @RequestParam(required = false) Long ordemServicoId,
            @RequestParam(required = false) StatusOrcamento status,
            @PageableDefault(size = 20) Pageable pageable) {
        return orcamentoService.listar(ordemServicoId, status, pageable);
    }

    @GetMapping("/{id}")
    public OrcamentoResponse buscar(@PathVariable Long id) {
        return orcamentoService.buscarPorId(id);
    }

    @PostMapping("/{id}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemOrcamentoResponse adicionarItem(@PathVariable Long id,
                                               @Valid @RequestBody ItemOrcamentoRequest request) {
        return orcamentoService.adicionarItem(id, request);
    }

    @DeleteMapping("/{id}/itens/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerItem(@PathVariable Long id, @PathVariable Long itemId) {
        orcamentoService.removerItem(id, itemId);
    }

    @PostMapping("/{id}/enviar")
    public OrcamentoResponse enviar(@PathVariable Long id) {
        return orcamentoService.enviar(id);
    }

    @PostMapping("/{id}/aprovar")
    public OrcamentoResponse aprovar(@PathVariable Long id) {
        return orcamentoService.aprovar(id);
    }

    @PostMapping("/{id}/rejeitar")
    public OrcamentoResponse rejeitar(@PathVariable Long id) {
        return orcamentoService.rejeitar(id);
    }

    @PostMapping("/{id}/cancelar")
    public OrcamentoResponse cancelar(@PathVariable Long id) {
        return orcamentoService.cancelar(id);
    }

    @PostMapping("/{id}/expirar")
    public OrcamentoResponse expirar(@PathVariable Long id) {
        return orcamentoService.expirar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        orcamentoService.excluir(id);
    }
}
