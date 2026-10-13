package br.com.gearedlogistics.gearedlogistics.modules.ordemservico.dto;

import br.com.gearedlogistics.gearedlogistics.modules.ordemservico.domain.StatusOrdemServico;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrdemServicoResponse(
        Long id,
        String numero,
        StatusOrdemServico status,
        Integer progresso,
        Long clienteId,
        String clienteNome,
        Long veiculoId,
        String veiculoPlaca,
        String descricaoProblema,
        List<ServicoResponse> servicos,
        List<AplicacaoItemResponse> itensAplicados,
        BigDecimal valorMaoDeObra,
        BigDecimal valorItens,
        BigDecimal valorTotal,
        Instant dataAbertura,
        Instant dataConclusao,
        Instant atualizadoEm
) {
}
