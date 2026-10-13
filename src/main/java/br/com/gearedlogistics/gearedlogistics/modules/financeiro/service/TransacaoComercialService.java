package br.com.gearedlogistics.gearedlogistics.modules.financeiro.service;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TipoTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.AcordoComercialRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.PagamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.RegistrarPagamentoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.TransacaoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.VendaRequest;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface TransacaoComercialService {

    TransacaoResponse criarVenda(VendaRequest request);

    TransacaoResponse criarPagamento(PagamentoRequest request);

    TransacaoResponse criarAcordo(AcordoComercialRequest request);

    TransacaoResponse buscarPorId(Long id);

    PageResponse<TransacaoResponse> listar(Long clienteId, Long ordemServicoId, StatusTransacao status,
                                           TipoTransacao tipo, Pageable pageable);

    TransacaoResponse aprovar(Long id);

    TransacaoResponse cancelar(Long id);

    TransacaoResponse pagar(Long id);

    TransacaoResponse registrarPagamento(Long id, RegistrarPagamentoRequest request);

    TransacaoResponse marcarInadimplente(Long id);

    TransacaoResponse regularizar(Long id);

    void excluir(Long id);
}
