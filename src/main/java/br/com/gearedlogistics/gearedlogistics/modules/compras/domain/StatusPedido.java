package br.com.gearedlogistics.gearedlogistics.modules.compras.domain;

/**
 * Ciclo de vida do pedido de compra (ver diagrama de estados).
 * <pre>
 * Solicitado → EmProcessamento → Enviado → Entregue
 *      ↓              ↓                     ↓
 *  Cancelado      Cancelado             Extraviado
 * </pre>
 */
public enum StatusPedido {
    SOLICITADO,
    EM_PROCESSAMENTO,
    ENVIADO,
    ENTREGUE,
    EXTRAVIADO,
    CANCELADO
}
