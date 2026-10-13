package br.com.gearedlogistics.gearedlogistics.modules.catalogo.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemResponse;
import br.com.gearedlogistics.gearedlogistics.shared.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ItemMapper implements BaseMapper<ItemRequest, ItemResponse, Item> {

    @Override
    public Item toEntity(ItemRequest request) {
        Item item = new Item();
        aplicarDados(item, request);
        return item;
    }

    @Override
    public ItemResponse toResponse(Item entity) {
        return new ItemResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCodigoFabricante(),
                entity.getTipo(),
                entity.getPrecoCusto(),
                entity.getPrecoVenda(),
                calcularMargemLucro(entity),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    @Override
    public void updateEntity(Item entity, ItemRequest request) {
        aplicarDados(entity, request);
    }

    private void aplicarDados(Item item, ItemRequest request) {
        item.setNome(request.nome());
        item.setCodigoFabricante(request.codigoFabricante());
        item.setTipo(request.tipo());
        item.setPrecoCusto(request.precoCusto());
        item.setPrecoVenda(request.precoVenda());
    }

    /**
     * Margem de lucro em percentual: (preço de venda - custo) / preço de venda * 100.
     */
    private BigDecimal calcularMargemLucro(Item item) {
        BigDecimal custo = item.getPrecoCusto();
        BigDecimal venda = item.getPrecoVenda();
        if (custo == null || venda == null || venda.signum() == 0) {
            return null;
        }
        return venda.subtract(custo)
                .multiply(BigDecimal.valueOf(100))
                .divide(venda, 2, RoundingMode.HALF_UP);
    }
}
