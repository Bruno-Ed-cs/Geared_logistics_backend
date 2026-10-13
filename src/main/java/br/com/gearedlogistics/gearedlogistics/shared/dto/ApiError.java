package br.com.gearedlogistics.gearedlogistics.shared.dto;

import java.time.Instant;
import java.util.List;

/**
 * Corpo padrão de resposta de erro da API.
 */
public record ApiError(
        Instant dataHora,
        int status,
        String erro,
        String mensagem,
        String caminho,
        List<String> detalhes
) {
}
