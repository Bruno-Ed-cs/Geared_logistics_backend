package br.com.gearedlogistics.gearedlogistics.modules.estoque.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Local de armazenamento (depósito/prateleira).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estoques")
public class Estoque extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "localizacao", length = 150)
    private String localizacao;

    @Column(name = "capacidade")
    private Integer capacidade;
}
