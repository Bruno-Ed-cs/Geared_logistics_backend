package br.com.gearedlogistics.gearedlogistics.modules.financeiro.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.AcordoComercial;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.Pagamento;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TipoTransacao;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.TransacaoComercial;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.domain.Venda;
import br.com.gearedlogistics.gearedlogistics.modules.financeiro.dto.TransacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    public TransacaoResponse toResponse(TransacaoComercial transacao) {
        Venda venda = transacao instanceof Venda v ? v : null;
        Pagamento pagamento = transacao instanceof Pagamento p ? p : null;
        AcordoComercial acordo = transacao instanceof AcordoComercial a ? a : null;

        return new TransacaoResponse(
                transacao.getId(),
                tipo(transacao),
                transacao.getStatus(),
                transacao.getValorTotal(),
                transacao.getDataRealizacao(),
                transacao.getCliente().getId(),
                transacao.getCliente().getNome(),
                transacao.getOrdemServico() != null ? transacao.getOrdemServico().getId() : null,
                transacao.getOrdemServico() != null ? numeroOs(transacao.getOrdemServico().getId()) : null,
                venda != null ? venda.getFormaPagamento() : null,
                pagamento != null ? pagamento.getValorPago() : null,
                pagamento != null ? pagamento.getStatusPagamento() : null,
                pagamento != null ? pagamento.getDataQuitacao() : null,
                acordo != null ? acordo.getCondicoes() : null,
                acordo != null ? acordo.getNumeroParcelas() : null,
                transacao.getCriadoEm(),
                transacao.getAtualizadoEm());
    }

    public TipoTransacao tipo(TransacaoComercial transacao) {
        if (transacao instanceof Venda) {
            return TipoTransacao.VENDA;
        }
        if (transacao instanceof Pagamento) {
            return TipoTransacao.PAGAMENTO;
        }
        return TipoTransacao.ACORDO;
    }

    private String numeroOs(Long id) {
        return id == null ? null : String.format("OS-%06d", id);
    }
}
