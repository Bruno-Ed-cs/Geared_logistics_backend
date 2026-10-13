package br.com.gearedlogistics.gearedlogistics.modules.clientes.repository;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    boolean existsByCpfCnpj(String cpfCnpj);
}
