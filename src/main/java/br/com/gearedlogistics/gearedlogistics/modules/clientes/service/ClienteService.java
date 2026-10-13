package br.com.gearedlogistics.gearedlogistics.modules.clientes.service;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.dto.ClienteRequest;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.dto.ClienteResponse;
import br.com.gearedlogistics.gearedlogistics.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ClienteService {

    ClienteResponse criar(ClienteRequest request);

    ClienteResponse buscarPorId(Long id);

    PageResponse<ClienteResponse> listar(String nome, String cpfCnpj, Pageable pageable);

    ClienteResponse atualizar(Long id, ClienteRequest request);

    void excluir(Long id);

    Cliente buscarEntidade(Long id);
}
