package br.com.gearedlogistics.gearedlogistics.shared.exception;

/**
 * Erro de regra de negócio (ex.: e-mail duplicado). Mapeado para HTTP 409.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
