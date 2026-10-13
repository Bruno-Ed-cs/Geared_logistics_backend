package br.com.gearedlogistics.gearedlogistics.modules.veiculos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VeiculoRequest(
        @NotBlank @Size(max = 10)
        String placa,

        @Size(max = 50)
        String marca,

        @Size(max = 50)
        String modelo,

        @Size(max = 30)
        String tipo,

        @Min(1900) @Max(2100)
        Integer anoFabricacao,

        @NotNull
        Long clienteId
) {
}
