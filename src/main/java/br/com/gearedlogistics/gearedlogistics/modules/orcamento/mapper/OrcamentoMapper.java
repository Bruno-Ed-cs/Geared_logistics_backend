package br.com.gearedlogistics.gearedlogistics.modules.orcamento.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.ItemOrcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.Orcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.domain.StatusOrcamento;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.ItemOrcamentoResponse;
import br.com.gearedlogistics.gearedlogistics.modules.orcamento.dto.OrcamentoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class OrcamentoMapper {

    public OrcamentoResponse toResponse(Orcamento orcamento) {
        List<ItemOrcamentoResponse> itens = orcamento.getItens().stream()
                .map(this::toItemResponse)
                .toList();

        BigDecimal valorTotal = orcamento.getItens().stream()
                .map(this::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean vencido = orcamento.getStatus() == StatusOrcamento.ENVIADO
                && orcamento.getDataValidade() != null
                && orcamento.getDataValidade().isBefore(LocalDate.now());

        return new OrcamentoResponse(
                orcamento.getId(),
                orcamento.getOrdemServico().getId(),
                numeroOs(orcamento.getOrdemServico().getId()),
                orcamento.getStatus(),
                orcamento.getDataValidade(),
                vencido,
                orcamento.getObservacoes(),
                itens,
                valorTotal,
                orcamento.getCriadoEm(),
                orcamento.getAtualizadoEm());
    }

    public ItemOrcamentoResponse toItemResponse(ItemOrcamento item) {
        return new ItemOrcamentoResponse(
                item.getId(),
                item.getDescricao(),
                item.getTipo(),
                item.getQuantidade(),
                item.getValorUnitario(),
                subtotal(item),
                item.getItem() != null ? item.getItem().getId() : null,
                item.getItem() != null ? item.getItem().getNome() : null);
    }

    private BigDecimal subtotal(ItemOrcamento item) {
        return item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
    }

    private String numeroOs(Long id) {
        return id == null ? null : String.format("OS-%06d", id);
    }
}
