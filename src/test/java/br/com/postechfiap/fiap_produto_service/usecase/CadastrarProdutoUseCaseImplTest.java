package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CadastrarProdutoUseCaseImplTest {

    @InjectMocks
    private CadastrarProdutoUseCaseImpl cadastrarProdutoUseCase;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void deveCadastrarProdutoComSucesso() {
        var produtoRequest = new ProdutoRequest("Produto Teste", 100.0);
        var produtoSalvo = new Produto(1L, "Produto Teste", "SKU123", 100.0);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        ProdutoResponse response = cadastrarProdutoUseCase.execute(produtoRequest);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Produto Teste", response.nome());
        assertEquals(100.0, response.preco());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }


    @Test
    void deveLancarExcecao_QuandoErroAoSalvarNoBanco() {
        var produtoRequest = new ProdutoRequest("Produto Erro", 50.0);

        when(produtoRepository.save(any(Produto.class)))
                .thenThrow(new RuntimeException("Erro ao salvar produto"));

        var exception = assertThrows(RuntimeException.class,
                () -> cadastrarProdutoUseCase.execute(produtoRequest));

        assertEquals("Erro ao salvar produto", exception.getMessage());
    }
}
