package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain;

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
 * Item (peça/material) aplicado em uma ordem de serviço.
 * Guarda o preço praticado no momento da aplicação.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "aplicacoes_item")
public class AplicacaoItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_no_momento", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoNoMomento;
}
