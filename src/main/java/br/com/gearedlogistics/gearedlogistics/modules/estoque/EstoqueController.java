package br.com.gearedlogistics.gearedlogistics.modules.estoque;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.PosicaoEstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.service.EstoqueService;
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

import java.util.List;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponse criar(@Valid @RequestBody EstoqueRequest request) {
        return estoqueService.criar(request);
    }

    @GetMapping
    public PageResponse<EstoqueResponse> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 20) Pageable pageable) {
        return estoqueService.listar(nome, pageable);
    }

    @GetMapping("/{id}")
    public EstoqueResponse buscar(@PathVariable Long id) {
        return estoqueService.buscarPorId(id);
    }

    @GetMapping("/{id}/posicoes")
    public List<PosicaoEstoqueResponse> listarPosicoes(@PathVariable Long id) {
        return estoqueService.listarPosicoes(id);
    }

    @PutMapping("/{id}")
    public EstoqueResponse atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequest request) {
        return estoqueService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        estoqueService.excluir(id);
    }
}
