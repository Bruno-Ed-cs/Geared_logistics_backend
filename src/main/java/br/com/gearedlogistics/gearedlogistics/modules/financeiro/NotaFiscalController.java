package br.com.gearedlogistics.gearedlogistics.modules.financeiro;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusNotaFiscal;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalResponse;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.service.NotaFiscalService;
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
@RequestMapping("/api/notas-fiscais")
@RequiredArgsConstructor
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotaFiscalResponse criar(@Valid @RequestBody NotaFiscalRequest request) {
        return notaFiscalService.criar(request);
    }

    @GetMapping
    public PageResponse<NotaFiscalResponse> listar(
            @RequestParam(required = false) Long transacaoId,
            @RequestParam(required = false) StatusNotaFiscal status,
            @PageableDefault(size = 20) Pageable pageable) {
        return notaFiscalService.listar(transacaoId, status, pageable);
    }

    @GetMapping("/{id}")
    public NotaFiscalResponse buscar(@PathVariable Long id) {
        return notaFiscalService.buscarPorId(id);
    }

    @PostMapping("/{id}/validar")
    public NotaFiscalResponse validar(@PathVariable Long id) {
        return notaFiscalService.validar(id);
    }

    @PostMapping("/{id}/cancelar")
    public NotaFiscalResponse cancelar(@PathVariable Long id) {
        return notaFiscalService.cancelar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        notaFiscalService.excluir(id);
    }
}
