package br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain;

/**
 * Ciclo de vida do orçamento (ver diagrama de estados).
 * <pre>
 * Rascunho → Enviado → Aprovado / Rejeitado / Expirado
 *    ↓
 * Cancelado
 * </pre>
 */
public enum StatusOrcamento {
    RASCUNHO,
    ENVIADO,
    APROVADO,
    REJEITADO,
    EXPIRADO,
    CANCELADO
}
