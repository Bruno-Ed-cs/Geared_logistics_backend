package br.com.gearedlogistics.gearedlogistics.modules.catalogo.service;

import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.Item;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.domain.TipoItem;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemRequest;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.dto.ItemResponse;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.mapper.ItemMapper;
import br.com.gearedlogistics.gearedlogistics.modules.catalogo.repository.ItemRepository;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import br.com.gearedlogistics.gearedlogistics.shared.exception.BusinessException;
import br.com.gearedlogistics.gearedlogistics.shared.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private static final String RECURSO = "Item";

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemResponse criar(ItemRequest request) {
        validarCodigoFabricante(request.codigoFabricante(), null);
        Item item = itemMapper.toEntity(request);
        return itemMapper.toResponse(itemRepository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponse buscarPorId(Long id) {
        return itemMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ItemResponse> listar(String nome, TipoItem tipo, Pageable pageable) {
        Specification<Item> filtro = montarFiltro(nome, tipo);
        return PageResponse.from(itemRepository.findAll(filtro, pageable).map(itemMapper::toResponse));
    }

    @Override
    @Transactional
    public ItemResponse atualizar(Long id, ItemRequest request) {
        Item item = buscarEntidade(id);
        validarCodigoFabricante(request.codigoFabricante(), item.getCodigoFabricante());
        itemMapper.updateEntity(item, request);
        return itemMapper.toResponse(itemRepository.save(item));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        buscarEntidade(id);
        itemRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Item buscarEntidade(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
    }

    private void validarCodigoFabricante(String codigoFabricante, String codigoAtual) {
        if (StringUtils.hasText(codigoFabricante)
                && !codigoFabricante.equals(codigoAtual)
                && itemRepository.existsByCodigoFabricante(codigoFabricante)) {
            throw new BusinessException("Já existe um item com o código de fabricante " + codigoFabricante);
        }
    }

    private Specification<Item> montarFiltro(String nome, TipoItem tipo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(nome)) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if (tipo != null) {
                predicates.add(cb.equal(root.get("tipo"), tipo));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
