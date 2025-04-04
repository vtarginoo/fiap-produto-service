package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DeletarProdutoUseCaseTest {

    @InjectMocks
    private DeletarProdutoUseCaseImpl deletarProdutoUseCase;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void deveDeletarProdutoComSucesso() {
        // Arrange
        Long produtoId = 1L;
        var produtoExistente = new Produto(produtoId, "Produto Teste", "SKU123", 100.0);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente));
        doNothing().when(produtoRepository).delete(produtoExistente);

        //  Act
        String resultado = deletarProdutoUseCase.execute(produtoId);

        //  Assert
        assertNotNull(resultado);
        assertEquals("Produto com ID 1 foi deletado com sucesso!", resultado);

        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoRepository, times(1)).delete(produtoExistente);
    }

    @Test
    void deveLancarExcecao_QuandoProdutoNaoEncontrado() {
        // Arrange
        Long produtoId = 99L;

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(RuntimeException.class,
                () -> deletarProdutoUseCase.execute(produtoId));

        assertEquals("Produto não encontrado.", exception.getMessage());

        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoRepository, never()).delete(any(Produto.class));
    }
}
