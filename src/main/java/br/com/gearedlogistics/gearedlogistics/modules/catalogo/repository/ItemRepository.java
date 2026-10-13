package br.com.gearedlogistics.gearedlogistics.modules.catalogo.repository;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    boolean existsByCodigoFabricante(String codigoFabricante);
}
