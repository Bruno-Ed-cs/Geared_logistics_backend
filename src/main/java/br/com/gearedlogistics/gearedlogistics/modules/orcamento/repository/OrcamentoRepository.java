package br.com.gearedlogistics.gearedlogistics.modules.orcamento.repository;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long>, JpaSpecificationExecutor<Orcamento> {
}
