package br.com.gearedlogistics.gearedlogistics.modules.estoque.repository;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovimentacaoEstoqueRepository
        extends JpaRepository<MovimentacaoEstoque, Long>, JpaSpecificationExecutor<MovimentacaoEstoque> {
}
