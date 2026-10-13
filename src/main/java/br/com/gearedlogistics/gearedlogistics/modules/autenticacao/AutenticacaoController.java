package br.com.gearedlogistics.gearedlogistics.modules.autenticacao;

import br.com.gearedlogistics.gearedlogistics.modules.autenticacao.dto.LoginRequest;
import br.com.gearedlogistics.gearedlogistics.modules.autenticacao.dto.TokenResponse;
import br.com.gearedlogistics.gearedlogistics.modules.autenticacao.service.AutenticacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return autenticacaoService.autenticar(request);
    }
}
