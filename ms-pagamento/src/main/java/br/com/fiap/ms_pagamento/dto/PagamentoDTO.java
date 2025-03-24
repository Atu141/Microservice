package br.com.fiap.ms_pagamento.dto;

import br.com.fiap.ms_pagamento.entity.Pagamento;
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
    @Size(min = 2,max = 100, message = "Nome deve ter entre 2 a 100 caracteres")
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
    private Long formaDePagamentoId;

    public PagamentoDTO(Pagamento entity){
        id = entity.getId();
        valor = entity.getValor();
        nome = entity.getNome();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigoDeSeguranca = entity.getCodigoDeSeguranca();
        status = entity.getStatus();
        pedidoId = entity.getPeriodoId();
        formaDePagamentoId = entity.getFormaDePagamentoId();
    }

}
