package br.com.gearedlogistics.gearedlogistics.modules.catalogo.service;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemResponse criar(ItemRequest request);

    ItemResponse buscarPorId(Long id);

    PageResponse<ItemResponse> listar(String nome, TipoItem tipo, Pageable pageable);

    ItemResponse atualizar(Long id, ItemRequest request);

    void excluir(Long id);

    Item buscarEntidade(Long id);
}
