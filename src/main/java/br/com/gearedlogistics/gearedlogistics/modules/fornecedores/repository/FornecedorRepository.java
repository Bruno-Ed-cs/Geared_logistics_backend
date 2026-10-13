package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.repository;

import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FornecedorRepository
        extends JpaRepository<Fornecedor, Long>, JpaSpecificationExecutor<Fornecedor> {

    boolean existsByCnpj(String cnpj);
}
