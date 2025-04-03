package br.com.postechfiap.fiap_produto_service;

import br.com.postechfiap.fiap_produto_service.controller.ProdutoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarBadRequest_QuandoNomeEstiverVazio() throws Exception {
        var produtoRequestJson = """
            {
                "nome": "",
                "preco": 100.0
            }
        """;

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[0]").value("must not be blank"));
    }

    @Test
    void deveRetornarBadRequest_QuandoPrecoForNegativo() throws Exception {
        var produtoRequestJson = """
            {
                "nome": "Produto Teste",
                "preco": -10.0
            }
        """;

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[0]").value("must be greater than 0"));
    }
}
