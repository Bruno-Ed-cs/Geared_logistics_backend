package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Item de mão de obra (composição da ordem de serviço).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "servicos")
public class Servico extends BaseEntity {

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "valor_mao_de_obra", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMaoDeObra;
}
