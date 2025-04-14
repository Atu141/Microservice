package br.com.fiap.ms_pagamento.service;

import br.com.fiap.ms_pagamento.repository.PagamentoRepository;
import br.com.fiap.ms_pagamento.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class PagamentoServiceIT {
    @Autowired
    private PagamentoService service;
    @Autowired
    private PagamentoRepository repository;

    private Long existingId;
    private Long nomExistingId;
    private Long contTotalPagamentos;

    @BeforeEach
    void setup() throws Exception{
        existingId = 1L;
        nomExistingId = 100L;
        contTotalPagamentos = 6L;
    }

    @Test
    public void deletePagamentoShouldDeleteResourceWhenIdExistes(){
        service.deletePagamento(existingId);
        Assertions.assertEquals(contTotalPagamentos - 1, repository.count());
    }

    @Test
    public void deletePagamentoShouldThrowResorceNotFoundExcepitionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                () ->{
                    service.deletePagamento(nomExistingId);
                }
        );
    }

    @Test
    public void getAllShouldReturnListPagamentoDTO(){

        var result = service.getAll();
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(contTotalPagamentos, result.size());
        Assertions.assertEquals(Double.valueOf(35.55), result.get(0).getValor().doubleValue());
        Assertions.assertEquals("Amadeus Mozart", result.get(0).getNome());
        Assertions.assertEquals("Chiquinha Gonzaga", result.get(1).getNome());
        Assertions.assertNull(result.get(5).getNome());
    }
}
