package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain;

/**
 * Ciclo de vida da ordem de serviço (ver diagrama de estados).
 * <pre>
 * Aberta → EmAndamento → Concluída
 *   ↓           ↓
 * Cancelada ← Cancelada
 * </pre>
 */
public enum StatusOrdemServico {
    ABERTA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}
