package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Fornecedor de peças e materiais.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fornecedores")
public class Fornecedor extends BaseEntity {

    @Column(name = "nome_empresa", nullable = false, length = 150)
    private String nomeEmpresa;

    @Column(name = "cnpj", nullable = false, length = 20, unique = true)
    private String cnpj;

    @Column(name = "email", length = 180)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;
}
