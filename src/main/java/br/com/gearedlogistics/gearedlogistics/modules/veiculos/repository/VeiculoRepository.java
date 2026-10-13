package br.com.gearedlogistics.gearedlogistics.modules.veiculos.repository;

import br.com.gearedlogistics.gearedlogistics.modules.veiculos.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    boolean existsByPlaca(String placa);
}
