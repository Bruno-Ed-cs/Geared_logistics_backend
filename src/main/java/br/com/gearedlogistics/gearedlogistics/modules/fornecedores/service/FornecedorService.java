package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.service;

import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain.Fornecedor;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorRequest;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface FornecedorService {

    FornecedorResponse criar(FornecedorRequest request);

    FornecedorResponse buscarPorId(Long id);

    PageResponse<FornecedorResponse> listar(String nome, String cnpj, Pageable pageable);

    FornecedorResponse atualizar(Long id, FornecedorRequest request);

    void excluir(Long id);

    Fornecedor buscarEntidade(Long id);
}
