package br.com.gearedlogistics.gearedlogistics.modules.fornecedores.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.domain.Fornecedor;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorRequest;
import br.com.gearedlogistics.gearedlogistics.modules.fornecedores.dto.FornecedorResponse;
import br.com.gearedlogistics.gearedlogistics.shared.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class FornecedorMapper implements BaseMapper<FornecedorRequest, FornecedorResponse, Fornecedor> {

    @Override
    public Fornecedor toEntity(FornecedorRequest request) {
        Fornecedor fornecedor = new Fornecedor();
        aplicarDados(fornecedor, request);
        return fornecedor;
    }

    @Override
    public FornecedorResponse toResponse(Fornecedor entity) {
        return new FornecedorResponse(
                entity.getId(),
                entity.getNomeEmpresa(),
                entity.getCnpj(),
                entity.getEmail(),
                entity.getTelefone(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    @Override
    public void updateEntity(Fornecedor entity, FornecedorRequest request) {
        aplicarDados(entity, request);
    }

    private void aplicarDados(Fornecedor fornecedor, FornecedorRequest request) {
        fornecedor.setNomeEmpresa(request.nomeEmpresa());
        fornecedor.setCnpj(request.cnpj());
        fornecedor.setEmail(request.email());
        fornecedor.setTelefone(request.telefone());
    }
}
