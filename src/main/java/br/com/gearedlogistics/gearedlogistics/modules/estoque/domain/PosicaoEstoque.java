package br.com.gearedlogistics.gearedlogistics.modules.estoque.domain;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Quantidade atual de um item em um determinado estoque.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posicoes_estoque",
        uniqueConstraints = @UniqueConstraint(name = "uk_posicao_estoque_item",
                columnNames = {"estoque_id", "item_id"}))
public class PosicaoEstoque extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estoque_id", nullable = false)
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "quantidade_atual", nullable = false)
    private Integer quantidadeAtual = 0;
}
