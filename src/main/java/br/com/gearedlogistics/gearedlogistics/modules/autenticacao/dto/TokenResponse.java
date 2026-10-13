package br.com.gearedlogistics.gearedlogistics.modules.autenticacao.dto;

/**
 * Token JWT devolvido no login.
 *
 * @param token     o token em si
 * @param tipo      sempre "Bearer"
 * @param expiraEm  validade em segundos
 */
public record TokenResponse(String token, String tipo, long expiraEm) {
}
