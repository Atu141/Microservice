package br.com.fiap.ms_pagamento.controller;

import br.com.fiap.ms_pagamento.dto.PagamentoDTO;
import br.com.fiap.ms_pagamento.service.PagamentoService;
import br.com.fiap.ms_pagamento.service.exceptions.ResourceNotFoundException;
import br.com.fiap.ms_pagamento.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagamentoService service;
    private PagamentoDTO dto;
    private Long existingId;
    private Long nonExistingId;


    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;

        dto = Factory.createPagamentoDTO();

        List<PagamentoDTO> list = List.of(dto);

        //getAll
        Mockito.when(service.getAll()).thenReturn(list);

        //getById
        //Existe
        Mockito.when(service.getById(existingId)).thenReturn(dto);
        //Não Existe
        Mockito.when(service.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
    }


    @Test
    public void getAllShouldReturnListPagamentoDTO() throws Exception{
        ResultActions result = mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldRetrunPagamentoDTOWhenIdExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/pagamentos/{id}",existingId)
                .accept(MediaType.APPLICATION_JSON));
        //Assertions
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.valor").exists());
        result.andExpect(jsonPath("$.status").exists());
    }

    @Test
    public void geyByIdShouldTrhowResourceNotFundExceptionWhenIdDoesNotExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/pagamentos/{od}",nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        //Assertions
        result.andExpect(status().isNotFound());
    }
}
