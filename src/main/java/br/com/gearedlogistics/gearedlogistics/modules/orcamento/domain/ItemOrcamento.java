package br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Linha do orçamento: mão de obra ou peça/material.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itens_orcamento")
public class ItemOrcamento extends BaseEntity {

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoItemOrcamento tipo;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    /** Preenchido quando o item é do catálogo (tipo PECA). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
