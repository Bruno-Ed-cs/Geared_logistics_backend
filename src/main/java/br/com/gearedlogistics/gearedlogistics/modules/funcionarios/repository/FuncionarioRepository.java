package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.repository;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FuncionarioRepository
        extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {

    boolean existsByMatricula(String matricula);

    boolean existsByUsuarioId(Long usuarioId);
}
