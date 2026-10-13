package br.com.gearedlogistics.gearedlogistics.modules.catalogo;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemResponse;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.service.ItemService;
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
@RequestMapping("/api/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse criar(@Valid @RequestBody ItemRequest request) {
        return itemService.criar(request);
    }

    @GetMapping
    public PageResponse<ItemResponse> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) TipoItem tipo,
            @PageableDefault(size = 20) Pageable pageable) {
        return itemService.listar(nome, tipo, pageable);
    }

    @GetMapping("/{id}")
    public ItemResponse buscar(@PathVariable Long id) {
        return itemService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ItemResponse atualizar(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
        return itemService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        itemService.excluir(id);
    }
}
