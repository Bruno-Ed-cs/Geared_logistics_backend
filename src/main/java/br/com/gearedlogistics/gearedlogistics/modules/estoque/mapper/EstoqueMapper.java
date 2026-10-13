package br.com.gearedlogistics.gearedlogistics.modules.estoque.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.estoque.domain.Estoque;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueRequest;
import br.com.gearedlogistics.gearedlogistics.modules.estoque.dto.EstoqueResponse;
import br.com.gearedlogistics.gearedlogistics.shared.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class EstoqueMapper implements BaseMapper<EstoqueRequest, EstoqueResponse, Estoque> {

    @Override
    public Estoque toEntity(EstoqueRequest request) {
        Estoque estoque = new Estoque();
        aplicarDados(estoque, request);
        return estoque;
    }

    @Override
    public EstoqueResponse toResponse(Estoque entity) {
        return new EstoqueResponse(
                entity.getId(),
                entity.getNome(),
                entity.getLocalizacao(),
                entity.getCapacidade(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    @Override
    public void updateEntity(Estoque entity, EstoqueRequest request) {
        aplicarDados(entity, request);
    }

    private void aplicarDados(Estoque estoque, EstoqueRequest request) {
        estoque.setNome(request.nome());
        estoque.setLocalizacao(request.localizacao());
        estoque.setCapacidade(request.capacidade());
    }
}
