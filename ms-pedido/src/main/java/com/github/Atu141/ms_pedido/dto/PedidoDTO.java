package com.github.Atu141.ms_pedido.dto;

import com.github.Atu141.ms_pedido.entities.ItemDoPedido;
import com.github.Atu141.ms_pedido.entities.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoDTO {
    private Long id;
    @NotEmpty(message = "CPF requerido")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres")
    private String cpf;
    @NotEmpty(message = "Nome requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
    private String nome;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Status status;
    private List<@Valid ItemDoPedido> itens = new ArrayList<>();
}
