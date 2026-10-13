package br.com.gearedlogistics.gearedlogistics.shared.exception;

/**
 * Recurso não encontrado. Mapeado para HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " não encontrado(a) para o id " + id);
    }
}
