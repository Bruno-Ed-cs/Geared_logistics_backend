package br.com.gearedlogistics.gearedlogistics.modules.financeiro.service;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.StatusNotaFiscal;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalRequest;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface NotaFiscalService {

    NotaFiscalResponse criar(NotaFiscalRequest request);

    NotaFiscalResponse buscarPorId(Long id);

    PageResponse<NotaFiscalResponse> listar(Long transacaoId, StatusNotaFiscal status, Pageable pageable);

    NotaFiscalResponse validar(Long id);

    NotaFiscalResponse cancelar(Long id);

    void excluir(Long id);
}
