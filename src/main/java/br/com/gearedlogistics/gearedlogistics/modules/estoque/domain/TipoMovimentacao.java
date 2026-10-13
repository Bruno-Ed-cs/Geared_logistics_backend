package br.com.gearedlogistics.gearedlogistics.modules.estoque.domain;

/**
 * Natureza de uma movimentação de estoque.
 * <ul>
 *   <li>{@code ENTRADA} — soma ao saldo atual</li>
 *   <li>{@code SAIDA} — subtrai do saldo atual</li>
 *   <li>{@code AJUSTE} — define o saldo atual (inventário/balanço)</li>
 * </ul>
 */
public enum TipoMovimentacao {
    ENTRADA,
    SAIDA,
    AJUSTE
}
