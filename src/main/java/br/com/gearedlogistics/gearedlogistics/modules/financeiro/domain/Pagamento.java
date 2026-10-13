package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Pagamento de um serviço/transação, com suporte a pagamento parcial e inadimplência.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pagamentos")
@DiscriminatorValue("PAGAMENTO")
public class Pagamento extends TransacaoComercial {

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false, length = 30)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    @Column(name = "data_quitacao")
    private LocalDate dataQuitacao;
}
