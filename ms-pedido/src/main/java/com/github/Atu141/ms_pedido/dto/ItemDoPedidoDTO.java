package com.github.Atu141.ms_pedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDoPedidoDTO {
    private Long id;

    @NotNull(message = "Quantidade requerido")
    @Positive(message = "Quantidade deve ser um número positivo")
    private Integer quantidade;
    @NotNull(message = "Valor unitário requerido")
    @Positive(message = "Valor unitário deve ser um número positivo")
    private BigDecimal valorUnitario;
}
