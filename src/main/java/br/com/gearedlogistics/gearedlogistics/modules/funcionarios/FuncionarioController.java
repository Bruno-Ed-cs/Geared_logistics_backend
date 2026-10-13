package br.com.gearedlogistics.gearedlogistics.modules.funcionarios;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Cargo;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioResponse;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.service.FuncionarioService;
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
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponse criar(@Valid @RequestBody FuncionarioRequest request) {
        return funcionarioService.criar(request);
    }

    @GetMapping
    public PageResponse<FuncionarioResponse> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Cargo cargo,
            @PageableDefault(size = 20) Pageable pageable) {
        return funcionarioService.listar(nome, cargo, pageable);
    }

    @GetMapping("/{id}")
    public FuncionarioResponse buscar(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FuncionarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioRequest request) {
        return funcionarioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        funcionarioService.excluir(id);
    }
}
