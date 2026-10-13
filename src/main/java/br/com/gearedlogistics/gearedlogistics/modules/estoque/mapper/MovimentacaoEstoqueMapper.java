package br.com.gearedlogistics.gearedlogistics.modules.estoque.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.MovimentacaoEstoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.MovimentacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoEstoqueMapper {

    public MovimentacaoResponse toResponse(MovimentacaoEstoque entity) {
        return new MovimentacaoResponse(
                entity.getId(),
                entity.getEstoque().getId(),
                entity.getEstoque().getNome(),
                entity.getItem().getId(),
                entity.getItem().getNome(),
                entity.getTipo(),
                entity.getQuantidade(),
                entity.getQuantidadeResultante(),
                entity.getMotivo(),
                entity.getCriadoEm());
    }
}
