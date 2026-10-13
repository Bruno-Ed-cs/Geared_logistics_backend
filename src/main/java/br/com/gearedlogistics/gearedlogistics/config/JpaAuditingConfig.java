package br.com.gearedlogistics.gearedlogistics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Habilita o preenchimento automático de {@code criadoEm} / {@code atualizadoEm}
 * nas entidades que herdam de {@code BaseEntity}.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
