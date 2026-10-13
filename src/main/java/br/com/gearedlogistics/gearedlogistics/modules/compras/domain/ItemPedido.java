package br.com.gearedlogistics.gearedlogistics.modules.compras.domain;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Item de um pedido de compra (composição do Pedido).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itens_pedido")
public class ItemPedido extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "quantidade_solicitada", nullable = false)
    private Integer quantidadeSolicitada;

    @Column(name = "preco_negociado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoNegociado;
}
