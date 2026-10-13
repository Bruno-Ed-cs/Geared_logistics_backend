package br.com.gearedlogistics.gearedlogistics.modules.veiculos.domain;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "veiculos")
public class Veiculo extends BaseEntity {

    @Column(name = "placa", nullable = false, length = 10, unique = true)
    private String placa;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "tipo", length = 30)
    private String tipo;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
