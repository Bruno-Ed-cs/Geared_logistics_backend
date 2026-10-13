package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.AplicacaoItem;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.OrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.Servico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.StatusOrdemServico;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.AplicacaoItemResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.FuncionarioAlocadoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.OrdemServicoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto.ServicoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class OrdemServicoMapper {

    public OrdemServicoResponse toResponse(OrdemServico os) {
        List<ServicoResponse> servicos = os.getServicos().stream()
                .map(this::toServicoResponse)
                .toList();

        List<AplicacaoItemResponse> itensAplicados = os.getItensAplicados().stream()
                .map(this::toAplicacaoItemResponse)
                .toList();

        List<FuncionarioAlocadoResponse> funcionarios = os.getFuncionarios().stream()
                .map(f -> new FuncionarioAlocadoResponse(f.getId(), f.getNome(), f.getCargo()))
                .toList();

        BigDecimal valorMaoDeObra = os.getServicos().stream()
                .map(Servico::getValorMaoDeObra)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorItens = os.getItensAplicados().stream()
                .map(this::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrdemServicoResponse(
                os.getId(),
                numero(os.getId()),
                os.getStatus(),
                progresso(os.getStatus()),
                os.getCliente().getId(),
                os.getCliente().getNome(),
                os.getVeiculo().getId(),
                os.getVeiculo().getPlaca(),
                os.getDescricaoProblema(),
                servicos,
                itensAplicados,
                funcionarios,
                valorMaoDeObra,
                valorItens,
                valorMaoDeObra.add(valorItens),
                os.getCriadoEm(),
                os.getDataConclusao(),
                os.getAtualizadoEm());
    }

    public ServicoResponse toServicoResponse(Servico servico) {
        return new ServicoResponse(servico.getId(), servico.getDescricao(), servico.getValorMaoDeObra());
    }

    public AplicacaoItemResponse toAplicacaoItemResponse(AplicacaoItem aplicacao) {
        return new AplicacaoItemResponse(
                aplicacao.getId(),
                aplicacao.getItem().getId(),
                aplicacao.getItem().getNome(),
                aplicacao.getItem().getTipo(),
                aplicacao.getQuantidade(),
                aplicacao.getPrecoNoMomento(),
                subtotal(aplicacao));
    }

    private BigDecimal subtotal(AplicacaoItem aplicacao) {
        return aplicacao.getPrecoNoMomento().multiply(BigDecimal.valueOf(aplicacao.getQuantidade()));
    }

    private String numero(Long id) {
        return id == null ? null : String.format("OS-%06d", id);
    }

    private Integer progresso(StatusOrdemServico status) {
        return switch (status) {
            case ABERTA -> 0;
            case EM_ANDAMENTO -> 50;
            case CONCLUIDA -> 100;
            case CANCELADA -> 0;
        };
    }
}
