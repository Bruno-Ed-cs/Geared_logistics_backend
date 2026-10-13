package br.com.gearedlogistics.gearedlogistics.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Base para entidades de pessoas do domínio (ex.: Cliente, Funcionário).
 * <p>Além do id e das datas de auditoria ({@link BaseEntity}), define os dados
 * comuns: nome, CPF/CNPJ e endereço.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Pessoa extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, length = 20, unique = true)
    private String cpfCnpj;

    @Embedded
    private Endereco endereco;
}
