package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

/**
 * Ciclo de vida do pagamento (ver diagrama de estados).
 */
public enum StatusPagamento {
    PENDENTE,
    PARCIALMENTE_PAGO,
    QUITADO,
    INADIMPLENTE
}
