package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

/**
 * Ciclo de vida da transação comercial (ver diagrama de estados).
 * <pre>
 * Pendente → Aprovada → Paga
 *    ↓          ↓
 * Cancelada ← Cancelada
 * </pre>
 */
public enum StatusTransacao {
    PENDENTE,
    APROVADA,
    PAGA,
    CANCELADA
}
