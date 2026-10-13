package br.com.gearedlogistics.gearedlogistics.modules.financeiro.repository;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long>, JpaSpecificationExecutor<NotaFiscal> {

    boolean existsByNumeroDocumento(String numeroDocumento);

    boolean existsByTransacaoId(Long transacaoId);
}
