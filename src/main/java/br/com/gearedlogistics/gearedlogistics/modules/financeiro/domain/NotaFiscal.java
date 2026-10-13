package br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Documento fiscal vinculado a uma transação comercial.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notas_fiscais")
public class NotaFiscal extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transacao_id", nullable = false, unique = true)
    private TransacaoComercial transacao;

    @Column(name = "numero_documento", nullable = false, length = 60, unique = true)
    private String numeroDocumento;

    @Column(name = "impostos", precision = 10, scale = 2)
    private BigDecimal impostos;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusNotaFiscal status = StatusNotaFiscal.EMITIDA;
}
