package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.dto.ListaProdutoResponse;
import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BuscarProdutoUseCaseTest {

    @InjectMocks
    private BuscarProdutoUseCaseImpl buscarProdutoUseCase;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void deveRetornarListaDeProdutos_QuandoBuscarPorNomeOuSku() {
        // Arrange (Preparação)
        String query = "Produto Teste";
        List<Produto> produtosMock = List.of(
                new Produto(1L, "Produto Teste", "SKU123", 100.0),
                new Produto(2L, "Outro Produto", "SKU456", 200.0)
        );

        // Simulação do comportamento do repositório
        when(produtoRepository.findByNomeContainingIgnoreCaseOrSkuIgnoreCase(query, query))
                .thenReturn(produtosMock);

        // Act (Execução)
        ListaProdutoResponse response = buscarProdutoUseCase.execute(query);

        // Assert (Validação)
        assertNotNull(response);
        assertEquals(2, response.produtos().size());
        assertEquals("Produto Teste", response.produtos().get(0).nome());
        assertEquals("Outro Produto", response.produtos().get(1).nome());

        // Garantir que o método do repositório foi chamado corretamente
        verify(produtoRepository, times(1))
                .findByNomeContainingIgnoreCaseOrSkuIgnoreCase(query, query);
    }

    @Test
    void deveRetornarListaVazia_QuandoNenhumProdutoEncontrado() {
        // Arrange (Preparação)
        when(produtoRepository.findByNomeContainingIgnoreCaseOrSkuIgnoreCase(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        // Act (Execução)
        ListaProdutoResponse response = buscarProdutoUseCase.execute("Inexistente");

        // Assert (Validação)
        assertNotNull(response);
        assertTrue(response.produtos().isEmpty());

        // Garantir que o método do repositório foi chamado corretamente
        verify(produtoRepository, times(1))
                .findByNomeContainingIgnoreCaseOrSkuIgnoreCase(anyString(), anyString());
    }
}

