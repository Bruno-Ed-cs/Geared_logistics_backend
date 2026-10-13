package br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Item de catálogo: peça (revenda/uso em reparo) ou material (insumo).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itens")
public class Item extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "codigo_fabricante", length = 60, unique = true)
    private String codigoFabricante;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoItem tipo;

    @Column(name = "preco_custo", precision = 10, scale = 2)
    private BigDecimal precoCusto;

    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;
}
