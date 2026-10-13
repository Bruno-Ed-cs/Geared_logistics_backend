package br.com.gearedlogistics.gearedlogistics.modules.veiculos;

import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.service.VeiculoService;
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
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponse criar(@Valid @RequestBody VeiculoRequest request) {
        return veiculoService.criar(request);
    }

    @GetMapping
    public PageResponse<VeiculoResponse> listar(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) Long clienteId,
            @PageableDefault(size = 20) Pageable pageable) {
        return veiculoService.listar(placa, clienteId, pageable);
    }

    @GetMapping("/{id}")
    public VeiculoResponse buscar(@PathVariable Long id) {
        return veiculoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public VeiculoResponse atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequest request) {
        return veiculoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        veiculoService.excluir(id);
    }
}
