package br.com.gearedlogistics.gearedlogistics.modules.compras.domain;

import br.com.gearedlogistics.gearedlogistics.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Rastreamento logístico de um pedido (1:1).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rastreamentos")
public class Rastreamento extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @Column(name = "codigo_rastreio", nullable = false, length = 60)
    private String codigoRastreio;

    @Column(name = "status_localizacao", length = 255)
    private String statusLocalizacao;

    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;
}
