package br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain;

import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.OrdemServico;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Orçamento (proposta de custos) vinculado a uma ordem de serviço.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orcamentos")
public class Orcamento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOrcamento status = StatusOrcamento.RASCUNHO;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private List<ItemOrcamento> itens = new ArrayList<>();
}
