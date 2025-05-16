package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.dto.AtualizarProdutoDTO;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.exception.produto.ProdutoNotFoundException;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AtualizarProdutoUseCaseTest {

    @InjectMocks
    private AtualizarProdutoUseCaseImpl atualizarProdutoUseCase;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    void deveAtualizarProdutoComSucesso() {
        // Arrange
        var produtoExistente = new Produto(1L, "Produto Antigo", "SKU123", new BigDecimal("100.0"));
        var atualizarProdutoDTO = new AtualizarProdutoDTO(1L, new ProdutoRequest("Produto Novo", new BigDecimal("150.0")));

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoExistente);

        //  Act
        ProdutoResponse response = atualizarProdutoUseCase.execute(atualizarProdutoDTO);

        //  Assert (Validação)
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Produto Novo", response.nome());
        assertEquals(new BigDecimal("150.0"), response.preco());

        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(produtoExistente);
    }

    @Test
    void deveLancarExcecao_QuandoProdutoNaoEncontrado() {
        // Arrange
        var atualizarProdutoDTO = new AtualizarProdutoDTO(99L, new ProdutoRequest("Produto Inexistente", new BigDecimal("200.0")));

        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(ProdutoNotFoundException.class,
                () -> atualizarProdutoUseCase.execute(atualizarProdutoDTO));

        assertNotNull(exception);

        verify(produtoRepository, times(1)).findById(99L);
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
