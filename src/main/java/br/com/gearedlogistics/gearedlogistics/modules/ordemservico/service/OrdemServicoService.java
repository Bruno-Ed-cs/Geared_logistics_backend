package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.service;

import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.StatusOrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface OrdemServicoService {

    OrdemServicoResponse criar(OrdemServicoRequest request);

    OrdemServicoResponse buscarPorId(Long id);

    PageResponse<OrdemServicoResponse> listar(StatusOrdemServico status, Long clienteId, Long veiculoId,
                                              Pageable pageable);

    ServicoResponse adicionarServico(Long id, ServicoRequest request);

    AplicacaoItemResponse adicionarItem(Long id, AplicacaoItemRequest request);

    OrdemServicoResponse iniciar(Long id);

    OrdemServicoResponse concluir(Long id);

    OrdemServicoResponse cancelar(Long id);

    void excluir(Long id);
}
