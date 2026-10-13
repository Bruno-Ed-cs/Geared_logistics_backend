package br.com.gearedlogistics.gearedlogistics.modules.estoque.repository;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.PosicaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosicaoEstoqueRepository extends JpaRepository<PosicaoEstoque, Long> {

    Optional<PosicaoEstoque> findByEstoqueIdAndItemId(Long estoqueId, Long itemId);

    List<PosicaoEstoque> findByEstoqueId(Long estoqueId);
}
