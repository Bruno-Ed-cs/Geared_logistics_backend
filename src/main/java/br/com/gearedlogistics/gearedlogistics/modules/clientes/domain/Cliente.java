package br.com.gearedlogistics.gearedlogistics.modules.clientes.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.Pessoa;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente extends Pessoa {

    @Column(name = "email", length = 180)
    private String email;

    @Column(name = "profissao", length = 100)
    private String profissao;

    @Column(name = "informacao_contato", length = 255)
    private String informacaoContato;

    @ElementCollection
    @CollectionTable(name = "clientes_telefones", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "telefone", length = 20, nullable = false)
    private Set<String> telefones = new LinkedHashSet<>();
}
