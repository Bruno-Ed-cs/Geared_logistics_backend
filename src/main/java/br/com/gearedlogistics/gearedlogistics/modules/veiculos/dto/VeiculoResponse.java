package br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto;

import java.time.Instant;

public record VeiculoResponse(
        Long id,
        String placa,
        String marca,
        String modelo,
        String tipo,
        Integer anoFabricacao,
        Long clienteId,
        String clienteNome,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
