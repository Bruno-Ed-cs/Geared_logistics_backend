package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Venda de peça/serviço.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vendas")
@DiscriminatorValue("VENDA")
public class Venda extends TransacaoComercial {

    @Column(name = "forma_pagamento", length = 30)
    private String formaPagamento;
}
