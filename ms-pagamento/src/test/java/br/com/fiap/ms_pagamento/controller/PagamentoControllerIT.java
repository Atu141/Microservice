package br.com.fiap.ms_pagamento.controller;


import br.com.fiap.ms_pagamento.dto.PagamentoDTO;
import br.com.fiap.ms_pagamento.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Rollback DB
public class PagamentoControllerIT {

    @Autowired
    private MockMvc mockMvc;
    //Preparando os dados
    private Long existingId;
    private Long nonExistingId;
    private PagamentoDTO pagamentoDTO;
    //conventendo o onj para JSON p/ enviar as requisições
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 50L;
        pagamentoDTO = Factory.createPagamentoDTO();
    }

    @Test
    public void getAllShouldReturnListAllPagamentos() throws Exception {
        //Teste de integração
        mockMvc.perform(get("/pagamentos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].id").value(1))
                .andExpect(jsonPath("[0].nome").isString())
                .andExpect(jsonPath("[0].nome").value("Amadeus Mozart"))
                .andExpect(jsonPath("[5].status").value("CONFIRMADO"));
    }

    @Test
    public void getByIdShouldReturnPagamentoDTOWhuenIdExists() throws Exception {
        mockMvc.perform(get("/pagamentos/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("nome").isString())
                .andExpect(jsonPath("nome").value("Amadeus Mozart"))
                .andExpect(jsonPath("status").value("CRIADO"));
    }

    @Test
    public void getByIdShouldReturnNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/pagamentos/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createShouldReturnPagamantoDTO() throws Exception {
        pagamentoDTO = Factory.createPagamentoDTO();
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);

        mockMvc.perform(post("/pagamentos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.nome").value(pagamentoDTO.getNome()))
                .andExpect(jsonPath("$.status").value("CRIADO"));
    }

    @Test
    public void createShouldPersistPagamentoWithRequiredFilds() throws Exception {
        pagamentoDTO = Factory.createNewPagamentoDTOWithRequiredFilds();
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);
        mockMvc.perform(post("/pagamentos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists()) //Obrigatório
                .andExpect(jsonPath("$.valor").value(pagamentoDTO.getValor()))
                .andExpect(jsonPath("$.status").value("CRIADO"))
                .andExpect(jsonPath("$.nome").isEmpty()) //Não obrigatório
                .andExpect(jsonPath("$.validade").isEmpty()); //Não obrigatório
    }

    @Test
    @DisplayName("Create deve lançar exveption quando dados inváçidos e retornar status 422")
    public void createShouldThrowsExceptionWhenInvalidData() throws Exception {
        pagamentoDTO = Factory.createNewPagamentoDTOWithInvalidData();
        String bodyJson = objectMapper.writeValueAsString(pagamentoDTO);
        mockMvc.perform(post("/pagamentos")
                        .content(bodyJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                // ou
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldUpdateAndReturnPagamentoDTOWhenIdExists() throws Exception {
        // status 200
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);
        mockMvc.perform(put("/pagamentos/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.valor").value(pagamentoDTO.getValor()))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value("CRIADO"))
                .andExpect(status().is2xxSuccessful())
                // ou
                .andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);
        mockMvc.perform(put("/pagamentos/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void updateShouldThrowsExceptionWhenInvalidData() throws Exception{
        pagamentoDTO = Factory.createNewPagamentoDTOWithInvalidData();
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);
        mockMvc.perform(put("/pagamentos/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception{
        mockMvc.perform(delete("/pagamentos/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        mockMvc.perform(delete("/pagamentos/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
