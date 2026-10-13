package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

/**
 * Ciclo de vida da nota fiscal (ver diagrama de estados).
 */
public enum StatusNotaFiscal {
    EMITIDA,
    VALIDA,
    CANCELADA
}
