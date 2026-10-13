package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain;

import br.com.gearedlogistics.gearedlogistics.modules.usuarios.domain.Usuario;
import br.com.gearedlogistics.gearedlogistics.shared.domain.Pessoa;
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
 * Funcionário da oficina: Gerente (gestão) ou Mecânico (executa reparos).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "funcionarios")
public class Funcionario extends Pessoa {

    @Column(name = "matricula", nullable = false, length = 30, unique = true)
    private String matricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false, length = 20)
    private Cargo cargo;

    /** Específico do Mecânico. */
    @Column(name = "especialidade", length = 100)
    private String especialidade;

    /** Específico do Gerente. */
    @Column(name = "nivel_acesso_admin")
    private Integer nivelAcessoAdmin;

    @Column(name = "salario", precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    /** Usuário de acesso ao sistema (opcional). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
}
