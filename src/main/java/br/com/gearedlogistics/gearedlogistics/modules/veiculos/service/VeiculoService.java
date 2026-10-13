package br.com.gearedlogistics.gearedlogistics.modules.veiculos.service;

import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface VeiculoService {

    VeiculoResponse criar(VeiculoRequest request);

    VeiculoResponse buscarPorId(Long id);

    PageResponse<VeiculoResponse> listar(String placa, Long clienteId, Pageable pageable);

    VeiculoResponse atualizar(Long id, VeiculoRequest request);

    void excluir(Long id);
}
