package com.gamestore.api.controller;

import com.gamestore.api.dto.JogoDTO;
import com.gamestore.api.exception.EntidadeNaoEncontradaException;
import com.gamestore.api.model.Jogo;
import com.gamestore.api.model.Plataforma;
import com.gamestore.api.service.JogoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JogoService jogoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deveria devolver código HTTP 403 quando requisição de cadastro não contiver token")
    void cadastrarJogoCenario1() throws Exception {
        mockMvc.perform(post("/jogos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 403 quando USER tenta cadastrar um jogo")
    @WithMockUser(roles = "USER")
    void cadastrarJogoCenario2() throws Exception {
        mockMvc.perform(post("/jogos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando requisição de cadastro não contiver JSON")
    @WithMockUser(roles = "ADMIN")
    void cadastrarJogoCenario3() throws Exception {
        mockMvc.perform(post("/jogos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 201 quando cadastrar jogo com dados válidos como ADMIN")
    @WithMockUser(roles = "ADMIN")
    void cadastrarJogoCenario4() throws Exception {
        JogoDTO jogoValido = new JogoDTO("GTA VI", "Mundo aberto", new BigDecimal("550.50"), 2026, List.of(1L));
        String jsonValido = objectMapper.writeValueAsString(jogoValido);

        Jogo jogoSalvoNoBanco = new Jogo(1L, "GTA VI", "Mundo aberto", new BigDecimal("550.50"), 2026, List.of());

        Mockito.when(jogoService.salvar(Mockito.any(JogoDTO.class))).thenReturn(jogoSalvoNoBanco);

        mockMvc.perform(post("/jogos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 400 ao cadastrar jogo inválido")
    @WithMockUser(roles = "ADMIN")
    void cadastrarJogoInvalido() throws Exception {
        JogoDTO jogoInvalido = new JogoDTO("", "Mundo aberto", new BigDecimal("550.50"), 2026, List.of(1L));
        String jsonBody = objectMapper.writeValueAsString(jogoInvalido);

        mockMvc.perform(post("/jogos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 200 ao listar jogos")
    @WithMockUser(roles = "USER")
    void listarJogosCenario1() throws Exception {
        mockMvc.perform(get("/jogos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0].titulo").value("FIFA 29"))
                .andExpect(jsonPath("$[0].genero").value("Esportes"));
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 204 ao deletar jogo como ADMIN")
    @WithMockUser(roles = "ADMIN")
    void deletarJogoCenario1() throws Exception {
        Mockito.doNothing().when(jogoService).deletarPorId(any(Long.class));

        mockMvc.perform(delete("/jogos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 403 ao deletar jogo como USER")
    @WithMockUser(roles = "USER")
    void deletarJogoCenario2() throws Exception {
        Mockito.doNothing().when(jogoService).deletarPorId(any(Long.class));

        mockMvc.perform(delete("/jogos/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 404 ao deletar jogo inexistente")
    @WithMockUser(roles = "ADMIN")
    void deletarJogoCenario3() throws Exception {
        Mockito.doThrow(new EntidadeNaoEncontradaException("Jogo não encontrado")).when(jogoService).deletarPorId(1L);

        mockMvc.perform(delete("/jogos/{id}"))
                .andExpect(status().isNotFound());
    }
}

