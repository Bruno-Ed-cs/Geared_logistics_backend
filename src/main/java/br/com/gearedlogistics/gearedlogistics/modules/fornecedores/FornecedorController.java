package br.com.gearedlogistics.gearedlogistics.modules.fornecedores;

import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorRequest;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorResponse;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.service.FornecedorService;
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
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorResponse criar(@Valid @RequestBody FornecedorRequest request) {
        return fornecedorService.criar(request);
    }

    @GetMapping
    public PageResponse<FornecedorResponse> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cnpj,
            @PageableDefault(size = 20) Pageable pageable) {
        return fornecedorService.listar(nome, cnpj, pageable);
    }

    @GetMapping("/{id}")
    public FornecedorResponse buscar(@PathVariable Long id) {
        return fornecedorService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FornecedorResponse atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequest request) {
        return fornecedorService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        fornecedorService.excluir(id);
    }
}
