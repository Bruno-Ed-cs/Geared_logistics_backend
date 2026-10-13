package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Acordo comercial (parcelamento) com o cliente.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "acordos_comerciais")
@DiscriminatorValue("ACORDO")
public class AcordoComercial extends TransacaoComercial {

    @Column(name = "condicoes", length = 500)
    private String condicoes;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;
}
