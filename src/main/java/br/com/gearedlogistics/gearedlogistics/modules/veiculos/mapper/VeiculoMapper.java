package br.com.gearedlogistics.gearedlogistics.modules.veiculos.mapper;

import br.com.gearedlogistics.gearedlogistics.modules.clientes.domain.Cliente;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.domain.Veiculo;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoRequest;
import br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto.VeiculoResponse;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public Veiculo toEntity(VeiculoRequest request, Cliente cliente) {
        Veiculo veiculo = new Veiculo();
        aplicarDados(veiculo, request, cliente);
        return veiculo;
    }

    public VeiculoResponse toResponse(Veiculo entity) {
        return new VeiculoResponse(
                entity.getId(),
                entity.getPlaca(),
                entity.getMarca(),
                entity.getModelo(),
                entity.getTipo(),
                entity.getAnoFabricacao(),
                entity.getCliente().getId(),
                entity.getCliente().getNome(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm());
    }

    public void updateEntity(Veiculo entity, VeiculoRequest request, Cliente cliente) {
        aplicarDados(entity, request, cliente);
    }

    private void aplicarDados(Veiculo veiculo, VeiculoRequest request, Cliente cliente) {
        veiculo.setPlaca(request.placa());
        veiculo.setMarca(request.marca());
        veiculo.setModelo(request.modelo());
        veiculo.setTipo(request.tipo());
        veiculo.setAnoFabricacao(request.anoFabricacao());
        veiculo.setCliente(cliente);
    }
}
