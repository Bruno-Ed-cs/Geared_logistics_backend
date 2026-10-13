package br.com.gearedlogistics.gearedlogistics.modules.estoque.domain;

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

/**
 * Registro histórico de entrada, saída ou ajuste de um item no estoque.
 * A data da movimentação é o {@code criadoEm} herdado de {@link BaseEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estoque_id", nullable = false)
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoMovimentacao tipo;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    /** Saldo do item no estoque após esta movimentação. */
    @Column(name = "quantidade_resultante", nullable = false)
    private Integer quantidadeResultante;

    @Column(name = "motivo", length = 255)
    private String motivo;
}
