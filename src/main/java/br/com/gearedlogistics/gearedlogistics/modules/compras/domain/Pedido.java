package br.com.gearedlogistics.gearedlogistics.modules.compras.domain;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain.Fornecedor;
import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Pedido de compra feito a um fornecedor.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    /** Estoque que receberá os itens quando o pedido for entregue. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estoque_destino_id", nullable = false)
    private Estoque estoqueDestino;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDate dataSolicitacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusPedido status = StatusPedido.SOLICITADO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false)
    private List<ItemPedido> itens = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Rastreamento rastreamento;
}
