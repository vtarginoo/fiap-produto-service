package br.com.postechfiap.fiap_produto_service.controller;

import br.com.postechfiap.fiap_produto_service.dto.AtualizarProdutoDTO;
import br.com.postechfiap.fiap_produto_service.dto.ListaProdutoResponse;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.exception.produto.ProdutoNotFoundException;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.AtualizarProdutoUseCase;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.BuscarProdutoUseCase;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.CadastrarProdutoUseCase;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.DeletarProdutoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Mock
    private CadastrarProdutoUseCase cadastrarProdutoUseCase;

    @Mock
    private BuscarProdutoUseCase buscarProdutoUseCase;

    @Mock
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    @Mock
    private DeletarProdutoUseCase deletarProdutoUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("1.1 Cadastrar Produto - Sucesso")
    @Test
    void deveCadastrarProdutoComSucesso() throws Exception {
        // Arrange
        var produtoRequest = new ProdutoRequest("Produto Cadastrado", new BigDecimal("100.0"));
        var produtoResponse = new ProdutoResponse(2L, "Produto Cadastrado", "SKU123", new BigDecimal("100.0"));

        when(atualizarProdutoUseCase.execute(any(AtualizarProdutoDTO.class)))
                .thenReturn(produtoResponse);

        // Act & Assert
        mockMvc.perform(post("/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nome").value("Produto Cadastrado"))
                .andExpect(jsonPath("$.preco").value(100.0));
    }

    @Test
    @DisplayName("2.1 Buscar Produto - Sucesso")
    void deveBuscarProdutoComSucesso() throws Exception {
        // Arrange
        var listaProdutoResponse = new ListaProdutoResponse(
                List.of(new ProdutoResponse(1L, "Cadeira Gamer Ergonômica", "CAD-GAM-ERG6", new BigDecimal("599.0"))));

        when(buscarProdutoUseCase.execute("Cadeira")).thenReturn(listaProdutoResponse);

        // Act & Assert
        mockMvc.perform(get("/produto")
                        .param("query", "Cadeira"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtos[0].id").value(6))
                .andExpect(jsonPath("$.produtos[0].nome").value("Cadeira Gamer Ergonômica"));
    }

    @Test
    @DisplayName("2.2 Buscar Produto - Erro")
    void deveRetornarBadRequest_QuandoQueryDeBuscaEstiverVazia() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/produto")
                        .param("query", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("3.1 Atualizar Produto - Sucesso")
    void deveAtualizarProdutoComSucesso() throws Exception {
        // Arrange
        var produtoRequest = new ProdutoRequest("Produto Atualizado", new BigDecimal("150.0"));
        var produtoResponse = new ProdutoResponse(4L, "Produto Atualizado", "SKU123", new BigDecimal("150.0"));

        when(atualizarProdutoUseCase.execute(any(AtualizarProdutoDTO.class)))
                .thenReturn(produtoResponse);

        // Act & Assert
        mockMvc.perform(put("/produto/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"))
                .andExpect(jsonPath("$.preco").value(150.0));


    }

    @Test
    @DisplayName("3.2 Atualizar Produto - Erro")
    void deveRetornarBadRequest_QuandoAtualizarProdutoComNomeInvalido() throws Exception {
        // Arrange
        var produtoRequestJson = """
            {
                "nome": "",
                "preco": 100.0
            }
        """;

        // Act & Assert
        mockMvc.perform(put("/produto/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[0]").value("must not be blank"));
    }

    @Test
    @DisplayName("4.1 Deletar Produto - Sucesso")
    void deveDeletarProdutoComSucesso() throws Exception {
        // Arrange
        var produtoReposicao = Produto.builder().nome("Mesa de Cabeceira").preco(new BigDecimal("100.0")).build();

         produtoReposicao = produtoRepository.save(produtoReposicao);

         Long produtoId = produtoReposicao.getId();

        String mensagem = "Produto com ID " + produtoId + " foi deletado com sucesso!";

        when(deletarProdutoUseCase.execute(produtoId)).thenReturn(mensagem);

        // Act & Assert
        mockMvc.perform(delete("/produto/{id}", produtoId))
                .andExpect(status().isOk())
                .andExpect(content().string(mensagem));

    }

    @Test
    @DisplayName("4.2 Deletar Produto - Erro")
    void deveRetornarNotFound_QuandoDeletarProdutoInexistente() throws Exception {
        // Arrange
        Long produtoId = 99L;
        when(deletarProdutoUseCase.execute(produtoId))
                .thenThrow(new ProdutoNotFoundException());

        // Act & Assert
        mockMvc.perform(delete("/produto/{id}", produtoId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produto não encontrado."));
    }
}
