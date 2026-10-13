package br.com.gearedlogistics.gearedlogistics.modules.compras.repository;

import br.com.gearedlogistics.gearedlogistics.modules.compras.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
}
