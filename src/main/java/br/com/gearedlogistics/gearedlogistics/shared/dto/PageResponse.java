package br.com.gearedlogistics.gearedlogistics.shared.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Envelope de paginação usado nas listagens da API.
 */
public record PageResponse<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas
) {

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
