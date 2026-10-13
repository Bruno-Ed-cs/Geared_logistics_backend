package br.com.gearedlogistics.gearedlogistics.modules.funcionarios.service;

import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Cargo;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.domain.Funcionario;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioRequest;
import br.com.gearedlogistics.gearedlogistics.modules.funcionarios.dto.FuncionarioResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface FuncionarioService {

    FuncionarioResponse criar(FuncionarioRequest request);

    FuncionarioResponse buscarPorId(Long id);

    PageResponse<FuncionarioResponse> listar(String nome, Cargo cargo, Pageable pageable);

    FuncionarioResponse atualizar(Long id, FuncionarioRequest request);

    void excluir(Long id);

    Funcionario buscarEntidade(Long id);
}
