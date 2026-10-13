package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Funcionario;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.domain.Veiculo;
import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Ordem de serviço: documento central de um reparo.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ordens_servico")
public class OrdemServico extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "descricao_problema", nullable = false, length = 500)
    private String descricaoProblema;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOrdemServico status = StatusOrdemServico.ABERTA;

    @Column(name = "data_conclusao")
    private Instant dataConclusao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private List<Servico> servicos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private List<AplicacaoItem> itensAplicados = new ArrayList<>();

    /** Funcionários alocados (UC09). */
    @ManyToMany
    @JoinTable(name = "ordens_servico_funcionarios",
            joinColumns = @JoinColumn(name = "ordem_servico_id"),
            inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
    private Set<Funcionario> funcionarios = new LinkedHashSet<>();
}
