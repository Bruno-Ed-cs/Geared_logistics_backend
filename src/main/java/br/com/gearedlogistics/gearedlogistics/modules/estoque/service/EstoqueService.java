package br.com.gearedlogistics.gearedlogistics.modules.estoque.service;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.PosicaoEstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstoqueService {

    EstoqueResponse criar(EstoqueRequest request);

    EstoqueResponse buscarPorId(Long id);

    PageResponse<EstoqueResponse> listar(String nome, Pageable pageable);

    EstoqueResponse atualizar(Long id, EstoqueRequest request);

    void excluir(Long id);

    List<PosicaoEstoqueResponse> listarPosicoes(Long estoqueId);

    Estoque buscarEntidade(Long id);
}
