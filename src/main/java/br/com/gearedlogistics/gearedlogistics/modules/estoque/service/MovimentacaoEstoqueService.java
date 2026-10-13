package br.com.gearedlogistics.gearedlogistics.modules.estoque.service;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.TipoMovimentacao;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface MovimentacaoEstoqueService {

    MovimentacaoResponse registrar(MovimentacaoRequest request);

    PageResponse<MovimentacaoResponse> listar(Long itemId, Long estoqueId, TipoMovimentacao tipo, Pageable pageable);
}
