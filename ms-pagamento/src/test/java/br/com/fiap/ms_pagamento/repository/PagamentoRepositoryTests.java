package br.com.fiap.ms_pagamento.repository;

import br.com.fiap.ms_pagamento.entity.Pagamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class PagamentoRepositoryTests {

    @Autowired
    private PagamentoRepository repository;

    @Test
    public void deleteShouldDeletedObjectWhenIdExistis(){
        //Arange
        Long existingId = 1L;
        //Act
        repository.deleteById(existingId);
        //Asserts
        Optional<Pagamento> result = repository.findById(existingId);
        //Testa se existe um obj dentro do Optional
        Assertions.assertFalse(result.isPresent());
    }
}
