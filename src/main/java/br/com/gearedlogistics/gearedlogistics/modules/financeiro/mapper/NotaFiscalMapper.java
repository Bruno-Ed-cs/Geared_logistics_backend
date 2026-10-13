package br.com.gearedlogistics.gearedlogistics.modules.financeiro.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.NotaFiscal;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.NotaFiscalResponse;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalMapper {

    public NotaFiscalResponse toResponse(NotaFiscal notaFiscal) {
        return new NotaFiscalResponse(
                notaFiscal.getId(),
                notaFiscal.getTransacao().getId(),
                notaFiscal.getNumeroDocumento(),
                notaFiscal.getImpostos(),
                notaFiscal.getDataEmissao(),
                notaFiscal.getStatus(),
                notaFiscal.getCriadoEm(),
                notaFiscal.getAtualizadoEm());
    }
}
