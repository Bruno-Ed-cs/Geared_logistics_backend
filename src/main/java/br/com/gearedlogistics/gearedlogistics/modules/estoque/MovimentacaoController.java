package br.com.gearedlogistics.gearedlogistics.modules.estoque;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.service.MovimentacaoEstoqueService;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentacaoResponse registrar(@Valid @RequestBody MovimentacaoRequest request) {
        return movimentacaoEstoqueService.registrar(request);
    }

    @GetMapping
    public PageResponse<MovimentacaoResponse> listar(
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) Long estoqueId,
            @RequestParam(required = false) TipoMovimentacao tipo,
            @PageableDefault(size = 20) Pageable pageable) {
        return movimentacaoEstoqueService.listar(itemId, estoqueId, tipo, pageable);
    }
}
