package br.com.gearedlogistics.gearedlogistics.shared.mapper;

/**
 * Contrato de conversão entre entidade e DTOs, implementado por cada módulo.
 *
 * @param <I> DTO de entrada (request)
 * @param <O> DTO de saída (response)
 * @param <E> entidade de domínio
 */
public interface BaseMapper<I, O, E> {

    E toEntity(I request);

    O toResponse(E entity);

    void updateEntity(E entity, I request);
}
