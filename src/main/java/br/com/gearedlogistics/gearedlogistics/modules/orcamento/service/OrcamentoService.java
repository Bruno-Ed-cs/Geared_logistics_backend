package br.com.gearedlogistics.gearedlogistics.modules.orcamento.service;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.StatusOrcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface OrcamentoService {

    OrcamentoResponse criar(OrcamentoRequest request);

    OrcamentoResponse buscarPorId(Long id);

    PageResponse<OrcamentoResponse> listar(Long ordemServicoId, StatusOrcamento status, Pageable pageable);

    ItemOrcamentoResponse adicionarItem(Long id, ItemOrcamentoRequest request);

    void removerItem(Long id, Long itemId);

    OrcamentoResponse enviar(Long id);

    OrcamentoResponse aprovar(Long id);

    OrcamentoResponse rejeitar(Long id);

    OrcamentoResponse cancelar(Long id);

    OrcamentoResponse expirar(Long id);

    void excluir(Long id);
}
