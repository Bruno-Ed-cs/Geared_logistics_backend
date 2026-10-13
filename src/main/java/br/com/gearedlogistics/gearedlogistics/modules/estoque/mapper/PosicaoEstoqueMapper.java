package br.com.gearedlogistics.gearedlogistics.modules.estoque.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.PosicaoEstoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.PosicaoEstoqueResponse;
import org.springframework.stereotype.Component;

@Component
public class PosicaoEstoqueMapper {

    public PosicaoEstoqueResponse toResponse(PosicaoEstoque entity) {
        Integer quantidade = entity.getQuantidadeAtual();
        return new PosicaoEstoqueResponse(
                entity.getId(),
                entity.getEstoque().getId(),
                entity.getItem().getId(),
                entity.getItem().getNome(),
                entity.getItem().getTipo(),
                quantidade,
                quantidade != null && quantidade > 0,
                entity.getAtualizadoEm());
    }
}
