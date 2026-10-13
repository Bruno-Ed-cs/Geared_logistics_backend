package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.repository;

import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdemServicoRepository
        extends JpaRepository<OrdemServico, Long>, JpaSpecificationExecutor<OrdemServico> {
}
