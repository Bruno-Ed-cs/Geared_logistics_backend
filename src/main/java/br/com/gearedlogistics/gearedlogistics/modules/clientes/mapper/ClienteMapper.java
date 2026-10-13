package br.com.gearedlogistics.gearedlogistics.modules.clientes.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.dto.ClienteRequest;
import br.com.gearedlogistics.gearedlogistics.modules.clientes.dto.ClienteResponse;
import br.com.gearedlogistics.gearedlogistics.shared.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
public class ClienteMapper implements BaseMapper<ClienteRequest, ClienteResponse, Cliente> {

    @Override
    public Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        aplicarDados(cliente, request);
        return cliente;
    }

    @Override
    public ClienteResponse toResponse(Cliente entity) {
        return new ClienteResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getEmail(),
                entity.getProfissao(),
                entity.getInformacaoContato(),
                entity.getEndereco(),
                new LinkedHashSet<>(entity.getTelefones()),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    @Override
    public void updateEntity(Cliente entity, ClienteRequest request) {
        aplicarDados(entity, request);
    }

    private void aplicarDados(Cliente cliente, ClienteRequest request) {
        cliente.setNome(request.nome());
        cliente.setCpfCnpj(request.cpfCnpj());
        cliente.setEmail(request.email());
        cliente.setProfissao(request.profissao());
        cliente.setInformacaoContato(request.informacaoContato());
        cliente.setEndereco(request.endereco());

        cliente.getTelefones().clear();
        if (request.telefones() != null) {
            cliente.getTelefones().addAll(request.telefones());
        }
    }
}
