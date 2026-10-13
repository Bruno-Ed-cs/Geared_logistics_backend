package br.com.gearedlogistics.gearedlogistics.modules.financeiro.repository;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TransacaoComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransacaoComercialRepository
        extends JpaRepository<TransacaoComercial, Long>, JpaSpecificationExecutor<TransacaoComercial> {
}
