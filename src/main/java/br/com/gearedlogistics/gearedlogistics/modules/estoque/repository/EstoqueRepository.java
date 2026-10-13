package br.com.gearedlogistics.gearedlogistics.modules.estoque.repository;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>, JpaSpecificationExecutor<Estoque> {
}
