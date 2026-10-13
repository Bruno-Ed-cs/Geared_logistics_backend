package br.com.gearedlogistics.gearedlogistics.modules.ordemservico;

import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.StatusOrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.service.OrdemServicoService;
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
@RequestMapping("/api/ordens-servico")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoResponse criar(@Valid @RequestBody OrdemServicoRequest request) {
        return ordemServicoService.criar(request);
    }

    @GetMapping
    public PageResponse<OrdemServicoResponse> listar(
            @RequestParam(required = false) StatusOrdemServico status,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long veiculoId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ordemServicoService.listar(status, clienteId, veiculoId, pageable);
    }

    @GetMapping("/{id}")
    public OrdemServicoResponse buscar(@PathVariable Long id) {
        return ordemServicoService.buscarPorId(id);
    }

    @PostMapping("/{id}/servicos")
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoResponse adicionarServico(@PathVariable Long id, @Valid @RequestBody ServicoRequest request) {
        return ordemServicoService.adicionarServico(id, request);
    }

    @PostMapping("/{id}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public AplicacaoItemResponse adicionarItem(@PathVariable Long id,
                                               @Valid @RequestBody AplicacaoItemRequest request) {
        return ordemServicoService.adicionarItem(id, request);
    }

    @PostMapping("/{id}/iniciar")
    public OrdemServicoResponse iniciar(@PathVariable Long id) {
        return ordemServicoService.iniciar(id);
    }

    @PostMapping("/{id}/concluir")
    public OrdemServicoResponse concluir(@PathVariable Long id) {
        return ordemServicoService.concluir(id);
    }

    @PostMapping("/{id}/cancelar")
    public OrdemServicoResponse cancelar(@PathVariable Long id) {
        return ordemServicoService.cancelar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        ordemServicoService.excluir(id);
    }
}
