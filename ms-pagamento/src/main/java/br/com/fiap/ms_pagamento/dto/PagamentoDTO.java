package br.com.fiap.ms_pagamento.dto;

import br.com.fiap.ms_pagamento.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PagamentoDTO {

    private Long id;
    @NotNull(message = "Campo Obrigatorio")
    @Positive(message = "O valor do pagamento deve ser um numero positivo")
    private BigDecimal valor;
    @Size(max = 100, message = "Nome deve ter até 100 caracteres")
    private String nome;
    @Size(max = 19, message = "Numero do cartão deve ter no maximo 19 caracteres")
    private String numeroDoCartao;
    @Size(min = 5, max = 5, message = "Validade deve ter 5 caracteres")
    private String validade;
    @Size(min = 3, max = 3, message = "O codigo de segurança deve ter 3 caracteres")
    private String codigoDeSeguranca;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @NotNull(message = "Pedido ID é obrigatorio")
    private Long pedidoId;
    @NotNull(message = "Forma de Pagamento ID é obrigatorio")
    private Long formaDePaagamentoId;


}
